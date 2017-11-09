package br.inpe.ccst.eba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.Measurements;
import br.inpe.ccst.eba.repository.MeasurementsRepository;
import br.inpe.ccst.eba.service.MeasurementsService;

@Service("measurementsService")
public class MeasurementsServiceImpl implements MeasurementsService {
    @Autowired
    private MeasurementsRepository repository;

    @Override
    public List<Measurements> getMeasurements() {
        return this.repository.findAll();
    }

	@Override
	@Transactional
	public Measurements save(Measurements measurements) {	
		return this.repository.save(measurements);
	}
}
