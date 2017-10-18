package br.inpe.ccst.eba.service.impl;

import br.inpe.ccst.eba.repository.TaxonomyRepository;
import br.inpe.ccst.eba.service.TaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taxonomyService")
public class TaxonomyServiceImpl implements TaxonomyService {

    @Autowired
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Integer getCountOfRecords() {
        return this.taxonomyRepository.getCountOfRecords();
    }
}
