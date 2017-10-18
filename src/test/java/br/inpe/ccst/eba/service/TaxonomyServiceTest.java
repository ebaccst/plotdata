package br.inpe.ccst.eba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxonomyServiceTest {
	
	@Autowired
	private TaxonomyService service;
	
	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}

	@Test
	public void shouldGetCountOfRecords() {
		assertEquals(new Integer(16467), this.service.getCountOfRecords());
	}
}
