package br.inpe.ccst.eba;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class Impl {

	public static void main(String[] args) throws IOException {
		String filepath = "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\data\\done\\JARAUA_2017_RESUMO.csv";
		
		Reader in = new FileReader(filepath);
		CSVFormat csvFormat = CSVFormat.RFC4180
//				.withHeader("Parcela", "Obs_parcela", "Ano", "N", "Nome_comum", "Familia", "Genero", "Especie", "Altura", "Alt_medida_estimada", "DAP", "Morta", "Tipo")
				.withFirstRecordAsHeader()
				.withIgnoreEmptyLines()
				.withIgnoreSurroundingSpaces()
				.withDelimiter(';');
			
		Iterable<CSVRecord> records = csvFormat.parse(in);
		for (CSVRecord record : records) {
			System.out.println(record.getRecordNumber());
			System.out.println(record.get(0));
		    System.out.println(record.get("Parcela"));
			break;
		}
		
		
		LevenshteinDistance UNLIMITED_DISTANCE = new LevenshteinDistance();
		System.out.println(UNLIMITED_DISTANCE.apply("heitor", "h"));
	}

}
