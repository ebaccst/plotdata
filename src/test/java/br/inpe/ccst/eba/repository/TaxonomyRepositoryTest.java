package br.inpe.ccst.eba.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;

public class TaxonomyRepositoryTest extends AbstractTest {

	@Autowired
	private TaxonomyRepository repository;

	@Test
	public void shouldRepositoryNotBeNull() {
		assertNotNull(this.repository);
	}

	@Test
	public void shouldGetCountOfRows() throws Exception {
		assertEquals(new Integer(16467), this.repository.getCountOfRecords());
	}
}
