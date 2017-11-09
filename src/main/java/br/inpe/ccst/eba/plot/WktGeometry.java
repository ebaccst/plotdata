package br.inpe.ccst.eba.plot;

import java.io.IOException;
import java.nio.file.Path;

import com.vividsolutions.jts.geom.Geometry;

public interface WktGeometry {

	Geometry getGeometry(Path directory, String filename) throws IOException;

}
