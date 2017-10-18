package br.inpe.ccst.eba.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonNameRepositoryTest {
	
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
