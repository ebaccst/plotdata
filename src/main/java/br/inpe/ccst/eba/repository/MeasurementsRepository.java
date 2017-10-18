package br.inpe.ccst.eba.repository;

import br.inpe.ccst.eba.domain.impl.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
    @Query("select count(m) from Measurements m")
    Integer getCountOfRecords();
}
