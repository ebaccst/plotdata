package br.inpe.ccst.eba.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.Owner;
import br.inpe.ccst.eba.domain.impl.Plot;
import br.inpe.ccst.eba.domain.impl.Plot.PlotBuilder;
import br.inpe.ccst.eba.plot.WktGeometry;
import br.inpe.ccst.eba.repository.PlotRepository;
import br.inpe.ccst.eba.service.PlotService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("plotService")
public class PlotServiceImpl implements PlotService {
	
	@Autowired
	private PlotRepository repository;
	
	@Autowired
	private WktGeometry wkt;

	@Override
	public Plot getPlotByName(String name) {
		return this.repository.findByName(name);
	}

	@Override
	public Plot getPlotByOwnerName(String owner) {
		return this.repository.findByOwnerName(owner);
	}

	@Override
	public Plot getPlotByObservation(String observation) {
		return this.repository.findByObservation(observation);
	}

	@Override
	public Plot getPlotByTransect(String transect) {
		return this.repository.findByTransect(transect);
	}

	@Override
	@Transactional
	public Plot save(String name, Owner owner, String observation, String transect, String shapefileDirectory) {
		final PlotBuilder plotBuilder = Plot.builder()
			.name(name)
			.owner(owner)
			.observation(observation)
			.transect(transect);
		
		if (shapefileDirectory != null) {
			final Path directory = Paths.get(shapefileDirectory);
			if (directory.toFile().isDirectory()) {
				try {
					plotBuilder.geom(wkt.getGeometry(directory, name));
				} catch (IOException e) {
					log.error("Problems in plot '{}' to extract geometry from shapefile, got '{}'.", name, e.getMessage());
				} catch (Exception e) {
					log.error("Unknown error found in plot '{}' when try to extract geometry: {}.", name, e.getMessage());
				}
			} else {
				log.error("Plot '{}' does not have a shapefile directory.");
			}
		}

		return this.repository.save(plotBuilder.build());
	}

}
