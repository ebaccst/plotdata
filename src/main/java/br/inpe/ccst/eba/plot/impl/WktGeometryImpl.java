package br.inpe.ccst.eba.plot.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.context.annotation.Configuration;

import com.vividsolutions.jts.geom.Geometry;

import br.inpe.ccst.eba.plot.WktGeometry;

@Configuration("wktGeometry")
public class WktGeometryImpl implements WktGeometry {
	
	private static final String SHAPEFILE_EXTENSION = ".shp";
	private static final String SHAPEFILE_NOT_FOUND_MESSAGE = "Shapefile '%s' not found in directory '%s'.";

	@Override
	public Geometry getGeometry(Path directory, String filename) throws IOException {
		Optional<Path> shapefile = findShapefile(directory, filename);
		if (!shapefile.isPresent()) {
			throw new FileNotFoundException(String.format(SHAPEFILE_NOT_FOUND_MESSAGE, filename, directory));
		}

		final FileDataStore store = FileDataStoreFinder.getDataStore(shapefile.get().toFile());
		final SimpleFeatureSource featureSource = store.getFeatureSource();

		Object defaultGeometry = null;
		SimpleFeatureIterator iterator = featureSource.getFeatures().features();
		while (defaultGeometry == null || iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			defaultGeometry = feature.getDefaultGeometry();
		}

		iterator.close();
		store.dispose();
		return (Geometry) defaultGeometry;
	}
	
	private Optional<Path> findShapefile(Path directory, String filename) throws IOException {
		final Predicate<? super Path> filenameFilter = searchByFilename(filename);
		return Files.list(directory).filter(Files::isRegularFile).filter(filenameFilter).findAny();
	}

	private Predicate<? super Path> searchByFilename(String filename) {
		return path -> {
			boolean isValidExtension = path.toString().toLowerCase().endsWith(SHAPEFILE_EXTENSION);
			boolean isSearchFile = path.getFileName().toString().equalsIgnoreCase(filename + SHAPEFILE_EXTENSION);
			return isValidExtension && isSearchFile;
		};
	}
}
