package br.inpe.ccst.eba.repository;

import br.inpe.ccst.eba.domain.impl.Taxonomy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaxonomyRepository extends JpaRepository<Taxonomy, Long>{
    @Query("select count(t) from Taxonomy t")
    Integer getCountOfRecords();
}
