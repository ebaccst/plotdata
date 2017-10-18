package br.inpe.ccst.eba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.inpe.ccst.eba.service.CommonNameService;;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonNameServiceTest {

	@Autowired
	private CommonNameService service;

	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}

	@Test
	public void testGetCountOfRecords() {
		assertEquals(new Integer(11743), this.service.getCountOfRecords());
	}
}