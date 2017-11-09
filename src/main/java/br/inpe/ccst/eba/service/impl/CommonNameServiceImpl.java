package br.inpe.ccst.eba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;
import br.inpe.ccst.eba.repository.CommonNameRepository;
import br.inpe.ccst.eba.service.CommonNameService;
import br.inpe.ccst.eba.service.SuggestionService;

@Service("commonNameService")
public class CommonNameServiceImpl implements CommonNameService {
    @Autowired
    private CommonNameRepository repository;
    
    @Autowired
    private SuggestionService suggestion;


    @Override
	public synchronized CommonName getCommonName(String name) {
    	return this.repository.findByNameUsingFrequence(name);
	}
    
	@Override
	public synchronized CommonName getSuggestion(String name) {
		List<String> options = this.repository.findByNameLike(name);
		String sug = suggestion.getBestMatch(name, options);
		return this.getCommonName(sug);
	}

	@Override
	@Transactional
	public CommonName save(String name, Family family, Genus genus, Species species) {
		CommonName commonName = CommonName.builder()
			.name(name)
			.family(family)
			.genus(genus)
			.species(species)
			.build();
		
		return this.repository.save(commonName);
	}
}
