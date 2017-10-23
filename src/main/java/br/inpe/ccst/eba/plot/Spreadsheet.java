package br.inpe.ccst.eba.plot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.inpe.ccst.eba.domain.impl.InformationHeight;
import br.inpe.ccst.eba.plot.impl.NormalizerImpl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@EqualsAndHashCode
@Slf4j
public class Spreadsheet {
	private Long recordNumber;
	private String plot;
	private Integer year;
	private Integer treeId;
	private String commonName;
	private String family;
	private String genus;
	private String species;
	private Float height;
	private Double dap;
	private String informationPlot;
	private String informationTreeId;
	private InformationHeight informationHeight;
	private Boolean informationDead;
	private String informationType;

	private Spreadsheet(Long recordNumber, String plot, Integer year, Integer treeId, String commonName, String family,
			String genus, String species, Float height, Double dap, String informationPlot, String informationTreeId,
			InformationHeight informationHeight, Boolean informationDead, String informationType) {
		this.recordNumber = recordNumber;
		this.plot = plot;
		this.year = year;
		this.treeId = treeId;
		this.commonName = commonName;
		this.family = family;
		this.genus = genus;
		this.species = species;
		this.height = height;
		this.dap = dap;
		this.informationPlot = informationPlot;
		this.informationTreeId = informationTreeId;
		this.informationHeight = informationHeight;
		this.informationDead = informationDead;
		this.informationType = informationType;
	}

	public static SpreadsheetBuilder builder() {
		return new SpreadsheetBuilder();
	}
	
	@Override
    public String toString() {
        try {
            return new ObjectMapper()
                    .writer()
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return super.toString();
        }
    }

	@Slf4j
	public static class SpreadsheetBuilder {
		private static final Normalizer NORMALIZER = new NormalizerImpl();

		private static final String PATTERN_IS_NUMERIC = "[+-]?\\d*(\\.\\d+)?";
		private static final String PATTERN_IS_ALPHANUMERIC = "^[a-zA-Z0-9]*$";
		private static final Pattern PATTERN_TREE_ID = Pattern.compile("([0-9]+)(.*)");
		
		private static final String NA = "na";
		private static final String ERROR_EXCEL = "#valor!";
		private static final String MEASURED = "med";

		private Long recordNumber;
		private String plot;
		private Integer year;
		private Integer treeId;
		private String commonName;
		private String family;
		private String genus;
		private String species;
		private Float height;
		private Double dap;
		private String informationPlot;
		private String informationTreeId;
		private InformationHeight informationHeight;
		private Boolean informationDead;
		private String informationType;

		public SpreadsheetBuilder recordNumber(Long recordNumber) {
			this.recordNumber = recordNumber;
			return this;
		}

		public SpreadsheetBuilder plot(String plot) {
			if (isValid("plot", plot)) {
				this.plot = NORMALIZER.apply(plot);
			}

			return this;
		}

		public SpreadsheetBuilder year(String year) {
			String argument = "year";
			if (isValid(argument, year)) {
				this.year = asInteger(argument, year);
			}

			return this;
		}

		public SpreadsheetBuilder treeId(String treeId) {
			String argument = "treeId";
			Integer id = null;
			String info = null;
			if (isValid(argument, treeId)) {
				String value = NORMALIZER.apply(treeId, true);
				if (value.matches(PATTERN_IS_NUMERIC)) {
					id = asInteger(argument, value);
				} else if (value.matches(PATTERN_IS_ALPHANUMERIC)) {
					Matcher matcher = PATTERN_TREE_ID.matcher(value);
					while (matcher.find()) {
						id = asInteger(argument, matcher.group(1));
						info = matcher.group(2);
					}
				}
			}
			
			this.treeId = id;
			this.informationTreeId = info;
			return this;
		}

		public SpreadsheetBuilder commonName(String commonName) {
			if (isValid("commonName", commonName)) {
				this.commonName = NORMALIZER.apply(commonName);
			}

			return this;
		}

		public SpreadsheetBuilder family(String family) {
			if (isValid("family", family)) {
				this.family = NORMALIZER.apply(family);
			}

			return this;
		}

		public SpreadsheetBuilder genus(String genus) {
			if (isValid("genus", genus)) {
				this.genus = NORMALIZER.apply(genus);
			}

			return this;
		}

		public SpreadsheetBuilder species(String species) {
			if (isValid("species", species)) {
				this.species = NORMALIZER.apply(species);
			}

			return this;
		}

		public SpreadsheetBuilder height(String height) {
			String argument = "height";
			if (isValid(argument, height)) {
				this.height = asFloat(argument, NORMALIZER.apply(height));
				this.informationHeight = InformationHeight.MEASURED;
			} else {
				this.informationHeight = InformationHeight.ESTIMATED;
			}

			return this;
		}
		
		public SpreadsheetBuilder informationHeight(String informationHeight) {
			if (isValid("informationHeight", informationHeight)) {
				if (MEASURED.equalsIgnoreCase(NORMALIZER.apply(informationHeight))) {
					this.informationHeight = InformationHeight.MEASURED;
				} else {
					this.informationHeight = InformationHeight.ESTIMATED;
				}
			}

			return this;
		}

		public SpreadsheetBuilder dap(String dap) {
			String argument = "dap";
			if (isValid(argument, dap)) {
				this.dap = asDouble(argument, NORMALIZER.apply(dap));
			}

			return this;
		}

		public SpreadsheetBuilder informationPlot(String informationPlot) {
			if (isValid("informationPlot", informationPlot)) {
				this.informationPlot = NORMALIZER.apply(informationPlot);
			}

			return this;
		}

		public SpreadsheetBuilder informationDead(String informationDead) {
			if (NA.equalsIgnoreCase(informationDead)) {
				this.informationDead = false;
			} else {
				this.informationDead = true;
			}

			return this;
		}

		public SpreadsheetBuilder informationType(String informationType) {
			if (isValid("informationType", informationType)) {
				this.informationType = NORMALIZER.apply(informationType);
			}

			return this;
		}

		public Spreadsheet build() {
			return new Spreadsheet(recordNumber, plot, year, treeId, commonName, family, genus, species, height, dap,
					informationPlot, informationTreeId, informationHeight, informationDead, informationType);
		}

		private boolean isValid(String argument, String value) {
			if (value == null || NA.equalsIgnoreCase(value) || ERROR_EXCEL.equalsIgnoreCase(value)) {
				log.warn("Argument '{}' NA in record {}", argument, this.recordNumber);
				return false;
			}

			return true;
		}
		
		private Integer asInteger(String argument, String value) {
			Integer result = null;
			try {
				result = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				log.error("Error to parse '{}' to Integer in record {}", argument, this.recordNumber);
			}
			
			return result;
		}
		
		private Float asFloat(String argument, String value) {
			Float result = null;
			try {
				result = Float.valueOf(value);
			} catch (NumberFormatException e) {
				log.error("Error to parse '{}' to Float in record {}", argument, this.recordNumber);
			}
			
			return result;
		}
		
		private Double asDouble(String argument, String value) {
			Double result = null;
			try {
				result = Double.valueOf(value);
			} catch (NumberFormatException e) {
				log.error("Error to parse '{}' to Double in record {}", argument, this.recordNumber);
			}
			
			return result;
		}
	}
}
