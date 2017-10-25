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

public class FamilyRepositoryTest extends AbstractTest {
	
	@Autowired
	private FamilyRepository repository;

	@Test
	public void shouldRepositoryNotBeNull() {
		assertThat(this.repository, is(not(nullValue())));
	}
	
	@Test
	public void findByNameLike() {
		List<String> options = this.repository.findByNameLike("ann");
		
		assertThat(options.size(), is(equalTo(2)));
		assertThat(options.get(0), is(equalTo("annonaceae")));
		assertThat(options.get(1), is(equalTo("cannabaceae")));
	}

}
