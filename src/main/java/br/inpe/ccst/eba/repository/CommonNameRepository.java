package br.inpe.ccst.eba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inpe.ccst.eba.domain.impl.CommonName;

public interface CommonNameRepository extends JpaRepository<CommonName, Long> {
	@Query("select count(c) from CommonName c")
	Integer getCountOfRecords();

	@Query("select c.name from CommonName c where c.name like %?1% order by c.name")
	List<String> findByNameLike(String name);

	@Query(nativeQuery = true, value = "SELECT * FROM fselect_common_name(?1);")
	CommonName findByNameUsingFrequence(String name);
}
