package br.inpe.ccst.eba.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.CommonName;
import br.inpe.ccst.eba.repository.CommonNameRepository;;

public class CommonNameServiceTest extends AbstractTest {

	@Autowired
	private CommonNameService service;

	@MockBean
	private CommonNameRepository repositoryMock;

	@Before
	public void setUp() {
		when(this.repositoryMock.getCountOfRecords())
			.thenReturn(DEFAULT_COUNT_RECORDS);
		
		when(this.repositoryMock.findByNameLike(DEFAULT_COMMON_NAME_SIMILAR))
			.thenReturn(getDefaultOptionsCommonNames());
		
		when(this.repositoryMock.findByNameUsingFrequence(DEFAULT_COMMON_NAME_SIMILAR))
			.thenReturn(getDefaultCommonName());
		
	}

	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}

	@Test
	public void testGetCountOfRecords() {
		assertEquals(DEFAULT_COUNT_RECORDS, this.service.getCountOfRecords());
	}
	
	@Test
	public void shouldGetCommonNameByName() {
		CommonName commonName = this.service.getCommonName(DEFAULT_COMMON_NAME_SIMILAR);
		
		assertThat(commonName, is(not(nullValue())));
		assertThat(commonName.getName(), is(equalTo("biriba")));
		assertThat(commonName.getFamily().getName(), is(equalTo("annonaceae")));
		assertThat(commonName.getGenus().getName(), is(equalTo("annona")));
	}
	
	@Test
	public void shouldGetSuggestionCommonNames() {
		CommonName commonName = this.service.getSuggestion(DEFAULT_COMMON_NAME_SIMILAR);
		
		assertThat(commonName, is(not(nullValue())));
		assertThat(commonName.getName(), is(equalTo("biriba")));
		assertThat(commonName.getFamily().getName(), is(equalTo("annonaceae")));
		assertThat(commonName.getGenus().getName(), is(equalTo("annona")));
	}
}