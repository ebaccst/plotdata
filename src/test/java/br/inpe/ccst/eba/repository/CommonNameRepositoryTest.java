package br.inpe.ccst.eba.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.CommonName;

public class CommonNameRepositoryTest extends AbstractTest {

	@Autowired
	private CommonNameRepository repository;

	public void shouldRepositoryNotBeNull() {
		assertNotNull(this.repository);
	}

	@Test
	public void shouldGetCountOfRecords() {
		assertEquals(new Integer(11743), this.repository.getCountOfRecords());
	}
	
	@Test
	public void shouldFindByNameLike() {
		final List<String> commonNameLike = this.repository.findByNameLike(DEFAULT_COMMON_NAME_SIMILAR);

		assertThat(commonNameLike.size(), is(equalTo(2)));
		assertThat(commonNameLike.get(0), is(equalTo("biriba")));
		assertThat(commonNameLike.get(1), is(equalTo("biribazinho")));
	}
	
	
	@Test
	public void shouldFindByNameUsingFrequence() {
		CommonName commonName = this.repository.findByNameUsingFrequence(DEFAULT_COMMON_NAME_SIMILAR);

		assertThat(commonName, is(not(nullValue())));
		assertThat(commonName.getName(), is(equalTo("biriba")));
		assertThat(commonName.getFamily().getName(), is(equalTo("annonaceae")));
		assertThat(commonName.getGenus().getName(), is(equalTo("annona")));
	}
}
