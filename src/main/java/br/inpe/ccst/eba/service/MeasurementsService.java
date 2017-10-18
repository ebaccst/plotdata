package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.Measurements;

import java.util.List;

public interface MeasurementsService {
    List<Measurements> getMeasurements();

    Integer getCountOfRecords();
}
