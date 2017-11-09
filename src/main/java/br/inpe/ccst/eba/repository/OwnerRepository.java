package br.inpe.ccst.eba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inpe.ccst.eba.domain.impl.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	Owner findByName(String name);

	Owner findByInstitution(String institution);
}
