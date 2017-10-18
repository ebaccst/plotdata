package br.inpe.ccst.eba.service.impl;

import br.inpe.ccst.eba.repository.CommonNameRepository;
import br.inpe.ccst.eba.service.CommonNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonNameService")
public class CommonNameServiceImpl implements CommonNameService {
    @Autowired
    private CommonNameRepository repository;

    @Override
    public Integer getCountOfRecords() {
        return this.repository.getCountOfRecords();
    }
}
