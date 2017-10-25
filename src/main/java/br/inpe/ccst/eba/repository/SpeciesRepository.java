package br.inpe.ccst.eba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inpe.ccst.eba.domain.impl.Species;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
	@Query("select s.name from Species s where s.name like %?1% order by s.name")
	List<String> findByNameLike(String name);

	Species findByName(String name);
}
