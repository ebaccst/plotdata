package br.inpe.ccst.eba;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {
	protected static final Integer DEFAULT_COUNT_RECORDS = 1;
	
	protected static final String DEFAULT_COMMON_NAME_SIMILAR = "biriba";
	
	protected static final String DEFAULT_FAMILY_GENUS_NAME_LIKE = "ann";
	
	protected static final String DEFAULT_SPECIES_NAME_LIKE = "anna";
		
	protected List<String> getDefaultOptionsCommonNames() {
		return Arrays.asList(DEFAULT_COMMON_NAME_SIMILAR, "biribazinho");
	}
	
	protected List<String> getDefaultOptionsFamily() {
		return Arrays.asList(getDefaultFamily().getName(), "cannabaceae");
	}
	
	protected List<String> getDefaultOptionsGenus() {
		return Arrays.asList(getDefaultGenus().getName(), "annickia");
	}
	
	protected List<String> getDefaultOptionsSpecies() {
		return Arrays.asList(getDefaultSpecies().getName(), "stereospermum annamense");
	}
	
	protected CommonName getDefaultCommonName() {
		return CommonName.builder().name(DEFAULT_COMMON_NAME_SIMILAR).family(getDefaultFamily()).genus(getDefaultGenus()).build();
	}
	
	protected Family getDefaultFamily() {
		return Family.builder().name("annonaceae").build();
	}
	
	protected Genus getDefaultGenus() {
		return Genus.builder().name("annona").build();
	}
	
	protected Species getDefaultSpecies() {
		return Species.builder().name("antrocaryon nannanii").build();
	}
}
