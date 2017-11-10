package br.inpe.ccst.eba.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Information;
import br.inpe.ccst.eba.domain.impl.InformationHeight;
import br.inpe.ccst.eba.domain.impl.Measurements;
import br.inpe.ccst.eba.domain.impl.Owner;
import br.inpe.ccst.eba.domain.impl.Plot;
import br.inpe.ccst.eba.domain.impl.Species;
import br.inpe.ccst.eba.plot.PlotOwner;
import br.inpe.ccst.eba.plot.Record;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.plot.SpreadsheetWriter;
import br.inpe.ccst.eba.service.CommonNameService;
import br.inpe.ccst.eba.service.DataService;
import br.inpe.ccst.eba.service.MeasurementsService;
import br.inpe.ccst.eba.service.OwnerService;
import br.inpe.ccst.eba.service.PlotService;
import br.inpe.ccst.eba.service.TaxonomyService;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("dataService")
public class DataServiceImpl implements DataService {
		
	@Autowired
	private SpreadsheetWriter spreadsheetWriter;
	
	@Autowired
	@Qualifier("suggestionValidator")
	private SpreadsheetValidator suggestionValidator;

	@Autowired
	@Qualifier("globalNamesResolverValidator") 
	private SpreadsheetValidator globalNamesResolverValidator;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PlotService plotService;
	
	@Autowired
	private TaxonomyService taxonomyService;
	
	@Autowired
	private CommonNameService commonNameService;
	
	@Autowired
	private MeasurementsService measurementsService;

	@Override
	public void validate(Spreadsheet spreadsheet) {
		log.info("Validating Spreadsheet '{}'...", spreadsheet.getFilepath());
		String spreadsheetFilepath = spreadsheet.getFilepath();
		String suggestionErrorFilepath = newFileName(spreadsheetFilepath, "suggestion_");
		String gnrErrorFilepath = newFileName(spreadsheetFilepath, "gnr_");
		
		Map<Long, String> suggestionError= this.suggestionValidator.validate(spreadsheet);
		this.spreadsheetWriter.write(suggestionError, suggestionErrorFilepath);
		
		Map<Long, String> gnrError = this.globalNamesResolverValidator.validate(spreadsheet);
		this.spreadsheetWriter.write(gnrError, gnrErrorFilepath);
	}

	@Override
	@Transactional
	public void insert(PlotOwner plotOwner, Spreadsheet spreadsheet) {
		log.info("Inserting Spreadsheet '{}'...", spreadsheet.getFilepath());
		Map<String, Owner> ownerCache = new HashMap<>();
		Map<String, Plot> plotCache = new HashMap<>();
		Map<String, Family> familyCache = new HashMap<>();
		Map<String, Genus> genusCache = new HashMap<>();
		Map<String, Species> speciesCache = new HashMap<>();
		Map<String, CommonName> commonNameCache = new HashMap<>();
		
		
		spreadsheet.each(rec -> {
			final String recPlot = rec.getPlot();
			final String recFamily = rec.getFamily();
			final String recGenus = rec.getGenus();
			final String recSpecies = rec.getSpecies();
			final String recCommonName = rec.getCommonName();
			
			Owner owner = getOwnerManaged(plotOwner, ownerCache, recPlot);
			Plot plot = getPlotManaged(plotOwner, plotCache, rec, recPlot, owner);
			Family family = getFamilyManaged(familyCache, recFamily);
			Genus genus = getGenusManaged(genusCache, recGenus);
			Species species = getSpeciesManaged(speciesCache, recSpecies);
			CommonName commonName = getCommonNameManaged(commonNameCache, recCommonName, family, genus, species);

			Information information = getInformation(rec.getInformationHeight(), rec.getInformationTreeId(), rec.getInformationDead(), rec.getInformationType());
			Measurements measurements = Measurements.builder()
					.information(information)
					.plot(plot)
					.commonName(commonName)
					.family(family)
					.genus(genus)
					.species(species)
					.treeId(rec.getTreeId())
					.year(rec.getYear())
					.dap(rec.getDap())
					.height(rec.getHeight())
					.build();
			
			measurementsService.save(measurements);
		});
		
		log.info("Spreadsheet was successfully inserted in the database.");
	}
	
	@Override
	public void updateAGB() {
		this.plotService.updateAGB();
	}


	private Information getInformation(InformationHeight informationHeight, String informationTreeId, Boolean informationDead, String informationType) {
		return Information.builder()
				.height(informationHeight)
				.treeId(informationTreeId)
				.dead(informationDead)
				.type(informationType)
				.build();
	}

	private CommonName getCommonNameManaged(Map<String, CommonName> commonNameCache, final String recCommonName,
			Family family, Genus genus, Species species) {
		CommonName commonName = null;
		if (commonNameCache.containsKey(recCommonName)) {
			commonName = commonNameCache.get(recCommonName);
		} else if (recCommonName != null && (family != null || genus != null || species != null)) {
			commonName = commonNameService.getCommonName(recCommonName);
			if (commonName == null) {
				commonName = commonNameService.save(recCommonName, family, genus, species);
			}
			
			commonNameCache.put(recCommonName, commonName);
		}
		return commonName;
	}

	private Species getSpeciesManaged(Map<String, Species> speciesCache, final String recSpecies) {
		Species species = null;
		if (speciesCache.containsKey(recSpecies)) {
			species = speciesCache.get(recSpecies);
		} else if (recSpecies != null) {
			species = taxonomyService.findSpecies(recSpecies);
			if (species == null) {
				species = taxonomyService.saveSpecies(recSpecies);
			}
			
			speciesCache.put(recSpecies, species);
		}
		return species;
	}

	private Genus getGenusManaged(Map<String, Genus> genusCache, final String recGenus) {
		Genus genus = null;
		if (genusCache.containsKey(recGenus)) {
			genus = genusCache.get(recGenus);
		} else if (recGenus != null) {
			genus = taxonomyService.findGenus(recGenus);
			if (genus == null) {
				genus = taxonomyService.saveGenus(recGenus);
			}
			
			genusCache.put(recGenus, genus);
		}
		return genus;
	}

	private Family getFamilyManaged(Map<String, Family> familyCache, final String recFamily) {
		Family family = null;
		if (familyCache.containsKey(recFamily)) {
			family = familyCache.get(recFamily);
		} else if (recFamily != null) {
			family = taxonomyService.findFamily(recFamily);
			if (family == null) {
				family = taxonomyService.saveFamily(recFamily);
			}

			familyCache.put(recFamily, family);
		}
		return family;
	}

	private Plot getPlotManaged(PlotOwner plotOwner, Map<String, Plot> plotCache, Record rec, final String recPlot,
			Owner owner) {
		Plot plot = null;
		if (plotCache.containsKey(recPlot)) {
			plot = plotCache.get(recPlot);
		} else {
			plot = plotService.getPlotByName(recPlot);
			if (plot == null) {
				plot = plotService.save(recPlot, owner, rec.getInformationPlot(), plotOwner.transect(recPlot), plotOwner.getShapefileDirectory());
			}

			plotCache.put(recPlot, plot);
		}
		return plot;
	}

	private Owner getOwnerManaged(PlotOwner plotOwner, Map<String, Owner> ownerCache, final String recPlot) {
		Owner owner = null;
		if (ownerCache.containsKey(recPlot)) {
			owner = ownerCache.get(recPlot);
		} else {
			Owner ownerArgs = plotOwner.owner(recPlot);
			owner = ownerService.getOwnerByName(ownerArgs.getName());
			if (owner == null) {
				owner = ownerService.save(ownerArgs.getName(), ownerArgs.getInstitution());
			}

			ownerCache.put(recPlot, owner);
		}
		return owner;
	}
	
	private String newFileName(String filepath, String prefix) {
		File file = new File(filepath);
		File parent = new File(file.getParent());
		return new File(parent, prefix + file.getName()).toString();
	}
}
