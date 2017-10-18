package br.inpe.ccst.eba.repository;

import br.inpe.ccst.eba.domain.impl.CommonName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommonNameRepository extends JpaRepository<CommonName, Long> {
    @Query("select count(c) from CommonName c")
    Integer getCountOfRecords();
}
