package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.CommonName;

public interface CommonNameService {
    Integer getCountOfRecords();

    CommonName getCommonName(String name);
    
	CommonName getSuggestion(String name);
}
