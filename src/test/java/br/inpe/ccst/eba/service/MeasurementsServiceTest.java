package br.inpe.ccst.eba.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.repository.MeasurementsRepository;

public class MeasurementsServiceTest extends AbstractTest {
	@Autowired
	private MeasurementsService service;

	@MockBean
	private MeasurementsRepository repositoryMock;

	@Before
	public void setUp() {
	}

	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}
}