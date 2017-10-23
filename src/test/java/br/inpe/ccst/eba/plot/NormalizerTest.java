package br.inpe.ccst.eba.plot;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;


public class NormalizerTest extends AbstractTest {
	@Autowired
	private Normalizer normalizer;

	@Test
	public void shouldNotBeNullNormalizer() {
		assertThat(normalizer, is(notNullValue()));
	}
	
	@Test
	public void shouldRemoveAccent() {
		assertThat(normalizer.apply("gitó"), is(equalTo("gito")));
		assertThat(normalizer.apply("açaí"), is(equalTo("acai")));
	}
	
	@Test
	public void shouldReturnLowerCase() {
		assertThat(normalizer.apply("GITó"), is(equalTo("gito")));
		assertThat(normalizer.apply("AçaÍ"), is(equalTo("acai")));
	}
	
	@Test
	public void shouldRemoveLeadingAndTrailingWhitespace() {
		assertThat(normalizer.apply("GITó"), is(equalTo("gito")));
		assertThat(normalizer.apply("AçaÍ"), is(equalTo("acai")));
	}
	
	@Test
	public void shouldRemoveSingleQuotationMarks() {
		assertThat(normalizer.apply("'abacate"), is(equalTo("abacate")));
		assertThat(normalizer.apply("'abacate'"), is(equalTo("abacate")));
	}
	
	@Test
	public void shouldRemoveDoubleQuotationMarks() {
		assertThat(normalizer.apply("\"abacate"), is(equalTo("abacate")));
		assertThat(normalizer.apply("\"abacate\""), is(equalTo("abacate")));
	}
	
	@Test
	public void shouldReplaceHyphenBySpace() {
		assertThat(normalizer.apply("folha-branca"), is(equalTo("folha branca")));
	}
	
	@Test
	public void shouldReplaceTabBySpace() {
		assertThat(normalizer.apply("folha\tbranca"), is(equalTo("folha branca")));
	}
	
	@Test
	public void shouldRemoveMultiplesSpace() {
		assertThat(normalizer.apply("folha   branca  "), is(equalTo("folha branca")));
	}
	
	@Test
	public void shouldReplaceCommaByDot() {
		assertThat(normalizer.apply("1,76"), is(equalTo("1.76")));
	}
	
	@Test
	public void shouldRemovePrepositions() {
		assertThat(normalizer.apply("pata-de-vaca"), is(equalTo("pata vaca")));
		assertThat(normalizer.apply("pata-de-vaca-da-casa"), is(equalTo("pata vaca casa")));
	}
	
	@Test
	public void shouldJoinWords() {
		assertThat(normalizer.apply("pata-de-vaca", true), is(equalTo("patavaca")));
	}
}
