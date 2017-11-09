package br.inpe.ccst.eba.plot.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import br.inpe.ccst.eba.domain.impl.Owner;
import br.inpe.ccst.eba.plot.Normalizer;
import br.inpe.ccst.eba.plot.OwnerHeader;
import br.inpe.ccst.eba.plot.PlotOwner;
import br.inpe.ccst.eba.plot.PlotOwnerReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration("plotOwnerReader")
public class PlotOwnerReaderImpl implements PlotOwnerReader {

	private static final Charset CHARSET = StandardCharsets.ISO_8859_1;
	private static final String PATTERN_ERROR = "lidar";
	private static final String PATTERN_DOT = ".";
	private static final String TRANSECT_NAME_TEMPLATE = "T-%s";
	private static final int INTIAL_INDEX = 0;

	@Value("${app.spreadsheet.delimiter}")
	private char delimiter;
	
	@Autowired
	private Normalizer normalizer;

	@Override
	public PlotOwner read(String plotOwnerFilepath, String shapefileDirectory) {
		Map<String, Owner> owners = new HashMap<>();
		Map<String, String> transects = new HashMap<>();
		
		CSVFormat csvFormat = CSVFormat.RFC4180
				.withFirstRecordAsHeader()
				.withIgnoreEmptyLines()
				.withIgnoreSurroundingSpaces()
				.withTrim()
				.withDelimiter(delimiter);

		try (InputStream is = new FileInputStream(plotOwnerFilepath)) {
			Reader in = new InputStreamReader(is, CHARSET);
			Iterable<CSVRecord> records = csvFormat.parse(in);
			for (CSVRecord record : records) {
				String transect = record.get(OwnerHeader.TRANSECT.get());
				if (transect.toLowerCase().contains(PATTERN_ERROR)) {
					continue;
				}

				String plot = normalizer.apply(record.get(OwnerHeader.PLOT.get()));
				if (plot.indexOf(PATTERN_DOT) > INTIAL_INDEX) {
					plot = plot.substring(INTIAL_INDEX, plot.lastIndexOf(PATTERN_DOT));
				}

				if (!owners.containsKey(plot)) {
					owners.put(plot, Owner.builder()
							.name(normalizer.apply(record.get(OwnerHeader.OWNER.get())))
							.institution(normalizer.apply(record.get(OwnerHeader.INSTITUTION.get())))
							.build());
				}

				if (!transects.containsKey(plot)) {
					transect = String.format(TRANSECT_NAME_TEMPLATE, extractTransectNumber(transect));
					transects.put(plot, transect);
				}
			}
		} catch (FileNotFoundException e) {
			log.error("Spreedsheet not found in '{}': {}", plotOwnerFilepath, e.getMessage());
		} catch (IOException e) {
			log.error("Error to load spreedsheet '{}': {}", plotOwnerFilepath, e.getMessage());
		}
		
		return PlotOwner.builder().owners(owners).transects(transects).shapefileDirectory(shapefileDirectory).build();
	}
	
	private String extractTransectNumber(String filename) {
		final String patternIsNumeric = "[+-]?\\d*(\\.\\d+)?";
		final Pattern pattern = Pattern.compile("T*([0-9]+)");
		final String numberFormat = "%04d";
		
		if (filename.matches(patternIsNumeric)) {
			return String.format(numberFormat, Integer.valueOf(filename));
		} else {
			Matcher matcher = pattern.matcher(filename);
			if (matcher.find()) {
				return String.format(numberFormat, Integer.valueOf(matcher.group()));
			}
		}

		return filename;
	}

}
