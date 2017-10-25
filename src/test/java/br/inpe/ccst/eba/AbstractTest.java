package br.inpe.ccst.eba;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {
	protected static final Integer DEFAULT_COUNT_RECORDS = 1;
	
	protected static final String DEFAULT_COMMON_NAME_SIMILAR = "biriba";
		
	protected List<String> getDefaultOptionsCommonNames() {
		return Arrays.asList(DEFAULT_COMMON_NAME_SIMILAR, "biribazinho");
	}
	
	protected CommonName getDefaultCommonName() {
		Family family = Family.builder().name("annonaceae").build();
		Genus genus = Genus.builder().name("annona").build();
		return CommonName.builder().name(DEFAULT_COMMON_NAME_SIMILAR).family(family).genus(genus).build();
	}
}
