package br.inpe.ccst.eba.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;

public class SuggestionServiceTest extends AbstractTest {
	
	@Autowired
	private SuggestionService service;
	
	@Test
	public void shouldServiceBeNotNull() {
		assertThat(this.service, is(not(nullValue())));
	}

	@Test
		public void getBestMatch() {
			List<String> options = Arrays.asList("bbbbb", "aaaaa", "ccccc", "aaaac");
			
			assertThat(this.service.getBestMatch("aaaab", options), is(equalTo("aaaaa")));
			assertThat(this.service.getBestMatch("ddddd", options), is(equalTo("")));
			assertThat(this.service.getBestMatch("aa", options), is(equalTo("aaaaa")));
			assertThat(this.service.getBestMatch("a", options), is(equalTo("aaaaa")));
			assertThat(this.service.getBestMatch("aaac", options), is(equalTo("aaaac")));
		}

}
