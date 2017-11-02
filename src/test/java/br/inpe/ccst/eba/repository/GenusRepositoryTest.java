package br.inpe.ccst.eba.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.Genus;

public class GenusRepositoryTest extends AbstractTest {
	
	@Autowired
	private GenusRepository repository;

	@Test
	public void shouldRepositoryNotBeNull() {
		assertThat(this.repository, is(not(nullValue())));
	}
	
	@Test
	public void findByNameLike() {
		List<String> options = this.repository.findByNameLike("ann");
		
		assertThat(options.size(), is(equalTo(12)));
		assertThat(options.get(0), is(equalTo("anneslea")));
		assertThat(options.get(1), is(equalTo("annickia")));
	}
	
	@Test
	public void shouldFindByName() {
		Genus findByName = this.repository.findByName("anneslea");
		
		assertThat(findByName.getId(), is(not(nullValue())));
	}
}
