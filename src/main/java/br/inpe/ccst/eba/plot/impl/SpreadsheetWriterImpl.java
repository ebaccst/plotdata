package br.inpe.ccst.eba.plot.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import br.inpe.ccst.eba.plot.SpreadsheetWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration("spreadsheetWriter")
public class SpreadsheetWriterImpl implements SpreadsheetWriter{
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object [] FILE_HEADER = {"RecordNumber", "Error"};

	@Value("${app.spreadsheet.delimiter}")
	private char delimiter;

	@Override
	public void write(Map<Long, String> errors, String filepath) {
		CSVFormat csvFormat = CSVFormat.RFC4180
				.withRecordSeparator(NEW_LINE_SEPARATOR)
				.withDelimiter(delimiter);

		CSVPrinter csvFilePrinter = null;	
		try(BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath, true), CHARSET))) {
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFormat);
	        csvFilePrinter.printRecord(FILE_HEADER);
	
	        for (Iterator<Entry<Long, String>> iterator = errors.entrySet().iterator(); iterator.hasNext();) {
	        	Entry<Long, String> entry = iterator.next();
	        	csvFilePrinter.printRecord(String.valueOf(entry.getKey()), entry.getValue());
			}

	        fileWriter.flush();
			log.info("CSV file was created successfully.");	
		} catch (Exception e) {
			log.error("Error in CsvFileWriter: {}", e.getMessage());
		} finally {
			if (csvFilePrinter != null) {
				try {
					csvFilePrinter.close();
				} catch (IOException e) {                
	                log.error("Error while flushing/closing fileWriter/csvPrinter: {}", e.getMessage());
				}
			}
		}
		
	}
}
