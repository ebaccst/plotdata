package br.inpe.ccst.eba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inpe.ccst.eba.domain.impl.Family;

public interface FamilyRepository extends JpaRepository<Family, Long>{
	@Query("select f.name from Family f where f.name like %?1% order by f.name")
	List<String> findByNameLike(String name);

	Family findByName(String name);
}
