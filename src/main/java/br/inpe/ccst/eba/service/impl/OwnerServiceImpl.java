package br.inpe.ccst.eba.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.Owner;
import br.inpe.ccst.eba.repository.OwnerRepository;
import br.inpe.ccst.eba.service.OwnerService;

@Service("ownerService")
public class OwnerServiceImpl implements OwnerService {

	@Autowired
	private OwnerRepository repository;

	@Override
	public Owner getOwnerByName(String name) {
		return this.repository.findByName(name);
	}

	@Override
	public Owner getOwnerByInstitution(String institution) {
		return this.repository.findByInstitution(institution);
	}

	@Override
	@Transactional
	public Owner save(String name, String institution) {
		Owner owner = Owner.builder()
			.name(name)
			.institution(institution)
			.build();
		
		return this.repository.save(owner);
	}

}
