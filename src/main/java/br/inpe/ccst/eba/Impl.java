package br.inpe.ccst.eba;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.ResourceInfo;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class Impl {

	public static void main(String[] args) throws Exception {
		String filepath = "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\responsaveis_parcelas.csv";
	}
	
	private static File newFileName(String filepath, String prefix) {
		File file = new File(filepath);
		File parent = new File(file.getParent());
		return new File(parent, prefix + file.getName());
	}

	private static String extractTransectNumber(String filename) {
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
	
	private static void geometryExample() throws IOException {
		final String directory = "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\shp\\epsg_5880";
		final String filename = "alflor_pc";

		Optional<Path> shapefile = findShapefile(directory, filename);
		if (!shapefile.isPresent()) {
			throw new FileNotFoundException(String.format("Shapefile '%s' not found in directory '%s'.", filename, directory));
		}

		final FileDataStore store = FileDataStoreFinder.getDataStore(shapefile.get().toFile());
		final SimpleFeatureSource featureSource = store.getFeatureSource();
		final ResourceInfo info = featureSource.getInfo();
		final CoordinateReferenceSystem crs = info.getCRS();

		Object defaultGeometry = null;
		SimpleFeatureIterator iterator = featureSource.getFeatures().features();
		while (defaultGeometry == null || iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			defaultGeometry = feature.getDefaultGeometry();
		}
		
		System.out.println(info.getName());
		System.out.println(crs.getName());
		System.out.println(defaultGeometry);
	}
	
	private static Optional<Path> findShapefile(String directory, String filename) throws IOException {
		final Predicate<? super Path> searchByFilename = path -> {
			final String extension = ".shp";
			boolean isValidExtension = path.toString().toLowerCase().endsWith(extension);
			boolean isSearchFile = path.getFileName().toString().equalsIgnoreCase(filename + extension);
			return isValidExtension && isSearchFile;
		};

		return Files.list(Paths.get(directory)).filter(Files::isRegularFile).filter(searchByFilename).findAny();
	}

	private static void csvExample() throws IOException {
		String filepath = "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\data\\done\\JARAUA_2017_RESUMO.csv";

		InputStream is = new FileInputStream(filepath);
		Reader in = new InputStreamReader(is, "ISO-8859-1");
		CSVFormat csvFormat = CSVFormat.RFC4180
				// .withHeader("Parcela", "Obs_parcela", "Ano", "N", "Nome_comum", "Familia",
				// "Genero", "Especie", "Altura", "Alt_medida_estimada", "DAP", "Morta", "Tipo")
				.withFirstRecordAsHeader().withIgnoreEmptyLines().withIgnoreSurroundingSpaces().withDelimiter(';');

		Iterable<CSVRecord> records = csvFormat.parse(in);
		for (CSVRecord record : records) {
			System.out.println(record.get("Nome_comum"));
		}
	}
}
