package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.Owner;

public interface OwnerService {
	Owner getOwnerByName(String name);

	Owner getOwnerByInstitution(String institution);

	Owner save(String name, String institution);
}