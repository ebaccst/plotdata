package br.inpe.ccst.eba.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;

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
}
