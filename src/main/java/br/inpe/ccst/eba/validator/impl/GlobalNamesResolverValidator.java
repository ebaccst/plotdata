package br.inpe.ccst.eba.validator.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inpe.ccst.eba.consumer.GlobalNamesResolverAPI;
import br.inpe.ccst.eba.consumer.impl.GlobalNames;
import br.inpe.ccst.eba.consumer.impl.GlobalNamesData;
import br.inpe.ccst.eba.consumer.impl.GlobalNamesResponse;
import br.inpe.ccst.eba.plot.Record;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.util.Parallel;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Service("globalNamesResolverValidator")
public class GlobalNamesResolverValidator implements SpreadsheetValidator {
	private static final int MAX_SIZE = 300;
	private static final int MAX_THREADS = 10;
	private static final String FORMAT = "json";
	private static final boolean RESOLVE_ONCE = false;
	private static final boolean WITH_CONTEXT = false;
	private static final boolean BEST_MATCH_ONLY = true;
	private static final boolean PREFERRED_DATA_SOURCES = false;
	private static final boolean HEADER_ONLY = false;
	private static final String ERROR_MESSAGE = "%s '%s' not found in Global Names Resolver. Do you mean '%s'?";
	private static final String SPECIES = "Species";
	private static final String GENUS = "Genus";
	private static final String FAMILY = "Family";
	
	@Autowired
	private GlobalNamesResolverAPI globalNames;
	
	@Autowired
	private  Parallel parallel;

	@Override
	public synchronized Map<Long, String> validate(Spreadsheet input) {
		Map<Long, String> errors = new ConcurrentHashMap<>();
		if (input.size() > MAX_SIZE) {
			asyncSpreadsheetResolve(input, errors);
		} else {
			spreadsheetResolve(input, errors);
		}

		return errors;
	}

	private void populate(Set<String> species, Set<String> genus, Set<String> family, Record rec) {
		final String speciesName = rec.getSpecies();
		final String genusName = rec.getGenus();
		final String familyName = rec.getFamily();
		
		if (speciesName != null) {
			species.add(speciesName);
		}
		
		if (genusName != null) {
			genus.add(genusName);
		}
		
		if (familyName != null) {
			family.add(familyName);
		}
	}
	
	private Map<String, String> resolve(Collection<String> names, String argument) {
		Map<String, String> errors = new ConcurrentHashMap<>();
		if (!names.isEmpty()) {
			String name = String.join("|", names);

			Call<GlobalNamesResponse> call = globalNames.resolve(
					name, 
					FORMAT, 
					RESOLVE_ONCE, 
					WITH_CONTEXT,
					BEST_MATCH_ONLY, 
					HEADER_ONLY, 
					PREFERRED_DATA_SOURCES);

			try {
				Response<GlobalNamesResponse> execute = call.execute();
				if (execute.isSuccessful()) {
					processResponse(argument, errors, execute.body());
				} else {
					throw new IOException(execute.message());
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return errors;
	}

	private void processResponse(String argument, Map<String, String> errors, final GlobalNamesResponse response) {
		final List<GlobalNamesData> dataset = response.getData();
		if (dataset != null && !dataset.isEmpty()) {
			dataset.stream().forEach(data -> processResponseResults(argument, errors, data, data.getResults()));
		}
	}

	private void processResponseResults(String argument, Map<String, String> errors, GlobalNamesData data, final List<GlobalNames> results) {
		if(results != null && !results.isEmpty()) {
			results.stream().forEach(result -> {
				if (result != null) {
					final String suppliedName = data.getSuppliedName();
					final String canonicalForm = result.getCanonicalForm();
					
					log.info("Resolving {} '{}'...", argument, suppliedName);
					if (!suppliedName.equalsIgnoreCase(canonicalForm)) {
						final String errorMessage = String.format(ERROR_MESSAGE, argument, suppliedName, canonicalForm);
						errors.put(suppliedName, errorMessage);
						log.warn(errorMessage);
					}
				}
			});
		}
	}
	
	private void findRecordErrors(Map<Long, String> errors, Map<String, String> speciesResult, Map<String, String> genusResult, Map<String, String> familyResult, Record rec) {
		final String speciesName = rec.getSpecies();
		final String genusName = rec.getGenus();
		final String familyName = rec.getFamily();
		
		StringBuilder errorMessage = new StringBuilder();
		if (speciesName != null && speciesResult.containsKey(speciesName)) {
			errorMessage.append(speciesResult.get(speciesName));
		}
		
		if (genusName != null && genusResult.containsKey(genusName)) {
			errorMessage.append(genusResult.get(genusName));
		}
		
		if (familyName != null && familyResult.containsKey(familyName)) {
			errorMessage.append(familyResult.get(familyName));
		}
				
		if (errorMessage.length() > 0) {
			errors.put(rec.getRecordNumber(), errorMessage.toString());
		}
	}
	
	private void spreadsheetResolve(Spreadsheet input, Map<Long, String> errors) {
		Set<String> species = new HashSet<>();
		Set<String> genus = new HashSet<>();
		Set<String> family = new HashSet<>();
		input.each(rec -> populate(species, genus, family, rec));
		input.each(rec -> findRecordErrors(errors, resolve(species, SPECIES), resolve(genus, GENUS), resolve(family, FAMILY), rec));
	}
	
	private void asyncSpreadsheetResolve(Spreadsheet input, Map<Long, String> errors) {
		Set<String> species = new HashSet<>();
		Set<String> genus = new HashSet<>();
		Set<String> family = new HashSet<>();
		
		Map<String, String> speciesResult = new ConcurrentHashMap<>();
		Map<String, String> genusResult = new ConcurrentHashMap<>();
		Map<String, String> familyResult = new ConcurrentHashMap<>();
		
		input.each(rec -> populate(species, genus, family, rec));
		
		if (!species.isEmpty()) {
			asyncResolve(species, speciesResult, SPECIES);
		}
		
		if (!genus.isEmpty()) {
			asyncResolve(genus, genusResult, GENUS);
		}
		
		if (!family.isEmpty()) {
			asyncResolve(family, familyResult, FAMILY);
		}

		input.each(rec -> findRecordErrors(errors, speciesResult, genusResult, familyResult, rec));
	}
	
	private void asyncResolve(Set<String> names,  Map<String, String> results, String argument) {
		Integer chunkSize = names.size() / MAX_THREADS;
		List<Thread> resolvers = new ArrayList<>();
		if (chunkSize > 1) {
			List<List<String>> chunks = parallel.chunks(new ArrayList<>(names), chunkSize);
			chunks.forEach(chunk -> {
				Thread resolver = asyncResolve(results, argument, chunk);
				resolver.start();
				resolvers.add(resolver);
			});
		} else {
			Thread resolver = asyncResolve(results, argument, names);
			resolver.start();
			resolvers.add(resolver);
		}
		
		resolvers.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				Thread.currentThread().interrupt();
			}
		});
	}

	private Thread asyncResolve(Map<String, String> speciesResult, String argument, Collection<String> chunk) {
		return new Thread(() -> speciesResult.putAll(resolve(chunk, argument)));
	}
}

