package br.inpe.ccst.eba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inpe.ccst.eba.domain.impl.Genus;

public interface GenusRepository extends JpaRepository<Genus, Long> {
	@Query("select g.name from Genus g where g.name like %?1% order by g.name")
	List<String> findByNameLike(String name);

	Genus findByName(String name);
}
