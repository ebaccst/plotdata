package br.inpe.ccst.eba.service.impl;

import br.inpe.ccst.eba.domain.impl.Measurements;
import br.inpe.ccst.eba.repository.MeasurementsRepository;
import br.inpe.ccst.eba.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("measurementsService")
public class MeasurementsServiceImpl implements MeasurementsService {
    @Autowired
    private MeasurementsRepository repository;

    @Override
    public List<Measurements> getMeasurements() {
        return this.repository.findAll();
    }

    @Override
    public Integer getCountOfRecords() {
        return this.repository.getCountOfRecords();
    }
}
