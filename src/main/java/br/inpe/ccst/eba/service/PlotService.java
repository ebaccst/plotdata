package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.Owner;
import br.inpe.ccst.eba.domain.impl.Plot;

public interface PlotService {
	Plot getPlotByName(String name);

	Plot getPlotByOwnerName(String owner);

	Plot getPlotByObservation(String observation);

	Plot getPlotByTransect(String transect);

	Plot save(String name, Owner owner, String observation, String transect, String shapefileDirectory);

	void updateAGB();
}
