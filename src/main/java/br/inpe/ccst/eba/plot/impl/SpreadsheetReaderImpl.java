package br.inpe.ccst.eba.plot.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import br.inpe.ccst.eba.plot.Record;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.plot.Spreadsheet.SpreadsheetBuilder;
import br.inpe.ccst.eba.plot.SpreadsheetHeader;
import br.inpe.ccst.eba.plot.SpreadsheetReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration("spreadsheetReader")
public class SpreadsheetReaderImpl implements SpreadsheetReader {
	private static final Charset CHARSET = StandardCharsets.ISO_8859_1;

	@Value("${app.spreadsheet.delimiter}")
	private char delimiter;

	@Override
	public Spreadsheet read(String filepath) {
		CSVFormat csvFormat = CSVFormat.RFC4180
				.withFirstRecordAsHeader()
				.withIgnoreEmptyLines()
				.withIgnoreSurroundingSpaces()
				.withTrim()
				.withDelimiter(delimiter);

		SpreadsheetBuilder builder = Spreadsheet.builder();

		try (InputStream is = new FileInputStream(filepath)) {
			Reader in = new InputStreamReader(is, CHARSET);
			
			builder.filepath(filepath);
			Iterable<CSVRecord> records = csvFormat.parse(in);
			for (CSVRecord record : records) {
				Record rec = Record.builder()
						.recordNumber(record.getRecordNumber())
						.plot(record.get(SpreadsheetHeader.PLOT.get()))
						.informationPlot(record.get(SpreadsheetHeader.INFORMATION_PLOT.get()))
						.year(record.get(SpreadsheetHeader.YEAR.get()))
						.treeId(record.get(SpreadsheetHeader.TREE_ID.get()))
						.commonName(record.get(SpreadsheetHeader.COMMON_NAME.get()))
						.family(record.get(SpreadsheetHeader.FAMILY.get()))
						.genus(record.get(SpreadsheetHeader.GENUS.get()))
						.species(record.get(SpreadsheetHeader.SPECIES.get()))
						.height(record.get(SpreadsheetHeader.HEIGHT.get()))
						.dap(record.get(SpreadsheetHeader.DAP.get()))
						.informationDead(record.get(SpreadsheetHeader.INFORMATION_DEAD.get()))
						.informationType(record.get(SpreadsheetHeader.INFORMATION_TYPE.get()))
						.build();

				builder.record(rec);
			}
		} catch (FileNotFoundException e) {
			log.error("Spreedsheet not found in '{}': {}", filepath, e.getMessage());
		} catch (IOException e) {
			log.error("Error to load spreedsheet '{}': {}", filepath, e.getMessage());
		}

		return builder.build();
	}

}
