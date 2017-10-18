package br.inpe.ccst.eba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.repository.CommonNameRepository;;

public class CommonNameServiceTest extends AbstractTest {

	@Autowired
	private CommonNameService service;

	@MockBean
	private CommonNameRepository repositoryMock;

	@Before
	public void setUp() {
		when(this.repositoryMock.getCountOfRecords()).thenReturn(DEFAULT_COUNT_RECORDS);
	}

	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}

	@Test
	public void testGetCountOfRecords() {
		assertEquals(DEFAULT_COUNT_RECORDS, this.service.getCountOfRecords());
	}
}