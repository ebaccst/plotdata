package br.inpe.ccst.eba.service;

import java.util.List;

import br.inpe.ccst.eba.domain.impl.Measurements;

public interface MeasurementsService {
    List<Measurements> getMeasurements();
    
    Measurements save(Measurements measurements);
}
