package br.inpe.ccst.eba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inpe.ccst.eba.domain.impl.Plot;

public interface PlotRepository extends JpaRepository<Plot, Long> {
	Plot findByName(String name);

	@Query("select p from Plot p, Owner c where p.owner.name = c.name")
	Plot findByOwnerName(String owner);

	Plot findByObservation(String observation);

	Plot findByTransect(String transect);
}
