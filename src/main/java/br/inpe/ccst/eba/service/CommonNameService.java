package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;

public interface CommonNameService {

    CommonName getCommonName(String name);
    
	CommonName getSuggestion(String name);

	CommonName save(String name, Family family, Genus genus, Species species);
}
