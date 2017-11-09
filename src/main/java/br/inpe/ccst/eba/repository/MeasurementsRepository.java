package br.inpe.ccst.eba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inpe.ccst.eba.domain.impl.Measurements;

public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
	
}
