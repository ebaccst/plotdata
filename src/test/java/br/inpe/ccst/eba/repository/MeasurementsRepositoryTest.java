package br.inpe.ccst.eba.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;

public class MeasurementsRepositoryTest extends AbstractTest {
	@Autowired
	private MeasurementsRepository repository;

	@Test
	public void shouldRepositoryNotBeNull() {
		assertNotNull(this.repository);
	}

}
