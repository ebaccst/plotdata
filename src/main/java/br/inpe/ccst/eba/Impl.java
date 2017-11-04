package br.inpe.ccst.eba;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Impl {

	public static void main(String[] args) throws IOException {
		String filepath = "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\data\\done\\JARAUA_2017_RESUMO.csv";

		InputStream is = new FileInputStream(filepath);
		Reader in = new InputStreamReader(is, "ISO-8859-1");
		CSVFormat csvFormat = CSVFormat.RFC4180
				// .withHeader("Parcela", "Obs_parcela", "Ano", "N", "Nome_comum", "Familia",
				// "Genero", "Especie", "Altura", "Alt_medida_estimada", "DAP", "Morta", "Tipo")
				.withFirstRecordAsHeader()
				.withIgnoreEmptyLines()
				.withIgnoreSurroundingSpaces()
				.withDelimiter(';');

		Iterable<CSVRecord> records = csvFormat.parse(in);
		for (CSVRecord record : records) {
			System.out.println(record.get("Nome_comum"));
		}
	}
}
