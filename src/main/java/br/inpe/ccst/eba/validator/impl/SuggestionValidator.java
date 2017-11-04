package br.inpe.ccst.eba.validator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;
import br.inpe.ccst.eba.plot.Record;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.service.CommonNameService;
import br.inpe.ccst.eba.service.TaxonomyService;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("suggestionValidator")
public class SuggestionValidator implements SpreadsheetValidator {
	private static final String TABLE_NOT_FOUND_MESSAGE = "Family '%s', Genus '%s', Species '%s' and Common name '%s' not found in database.";
	private static final String COLUMN_NOT_FOUND_MESSAGE = "%s '%s' not found in database.";
	private static final String SUGGESTION_MESSAGE = " Do you mean '%s'?";
	private static final int MAX_SIZE = 1000;
	private static final int MAX_THREADS = 10;

	@Autowired
	private CommonNameService commonNameService;

	@Autowired
	private TaxonomyService taxonomyService;

	@Override
	public Map<Long, String> validate(Spreadsheet input) {
		Map<Long, String> errors = new HashMap<>();
		if (input.size() > MAX_SIZE) {
			input.eachChunk(MAX_THREADS, chunk -> asyncCheck(errors, chunk).start());
		} else {
			input.each(rec -> check(errors, rec));
		}

		return errors;
	}

	private Thread asyncCheck(Map<Long, String> errors, List<Record> chunk) {
		return new Thread(() -> chunk.forEach(rec -> check(errors, rec)));
	}

	private void check(Map<Long, String> errors, Record rec) {
		final String species = rec.getSpecies();
		final String genus = rec.getGenus();
		final String family = rec.getFamily();
		final String commonName = rec.getCommonName();

		final String speciesErrorMessage = checkSpecies(species);
		final String genusErrorMessage = checkGenus(genus);
		final String familyErrorMessage = checkFamily(family);
		final String commonNameErrorMessage = checkCommonName(commonName);

		int numberOfErrors = 0;
		String errorMessage = null;
		if (speciesErrorMessage != null) {
			numberOfErrors++;
			errorMessage = speciesErrorMessage;
		}

		if (genusErrorMessage != null) {
			numberOfErrors++;
			errorMessage = genusErrorMessage;
		}

		if (familyErrorMessage != null) {
			numberOfErrors++;
			errorMessage = familyErrorMessage;
		}

		if (commonNameErrorMessage != null) {
			numberOfErrors++;
			errorMessage = commonNameErrorMessage;
		}

		if (numberOfErrors > 1) {
			errorMessage = getTableNotFoundMessage(family, genus, species, commonName);
		}

		if (errorMessage != null) {
			errors.put(rec.getRecordNumber(), errorMessage);
		}
	}

	private String checkSpecies(String name) {
		String errorMessage = null;
		if (name != null) {
			Species species = this.taxonomyService.findSpecies(name);
			if (species == null) {
				errorMessage = getColumnNotFoundMessage("Species", name);
				species = this.taxonomyService.getSuggestionSpecies(name);
				if (species != null) {
					errorMessage += getSuggestionMessage(species.getName());
				}

				log.warn(errorMessage);
			}
		}

		return errorMessage;
	}

	private String checkGenus(String name) {
		String errorMessage = null;
		if (name != null) {
			Genus genus = this.taxonomyService.findGenus(name);
			if (genus == null) {
				errorMessage = getColumnNotFoundMessage("Genus", name);
				genus = this.taxonomyService.getSuggestionGenus(name);
				if (genus != null) {
					errorMessage += getSuggestionMessage(genus.getName());
				}

				log.warn(errorMessage);
			}
		}

		return errorMessage;
	}

	private String checkFamily(String name) {
		String errorMessage = null;
		if (name != null) {
			Family family = this.taxonomyService.findFamily(name);
			if (family == null) {
				errorMessage = getColumnNotFoundMessage("Family", name);
				family = this.taxonomyService.getSuggestionFamily(name);
				if (family != null) {
					errorMessage += getSuggestionMessage(family.getName());
				}

				log.warn(errorMessage);
			}
		}

		return errorMessage;
	}

	private String checkCommonName(String name) {
		String errorMessage = null;
		if (name != null) {
			CommonName commonName = this.commonNameService.getCommonName(name);
			if (commonName == null) {
				errorMessage = getColumnNotFoundMessage("Common name", name);
				commonName = this.commonNameService.getSuggestion(name);
				if (commonName != null) {
					errorMessage += getSuggestionMessage(commonName.getName());
				}

				log.warn(errorMessage);
			}
		}

		return errorMessage;
	}

	private String getTableNotFoundMessage(String family, String genus, String species, String commonName) {
		return String.format(TABLE_NOT_FOUND_MESSAGE, family, genus, species, commonName);
	}

	private String getColumnNotFoundMessage(String column, String value) {
		return String.format(COLUMN_NOT_FOUND_MESSAGE, column, value);
	}

	private String getSuggestionMessage(String value) {
		return String.format(SUGGESTION_MESSAGE, value);
	}
}
