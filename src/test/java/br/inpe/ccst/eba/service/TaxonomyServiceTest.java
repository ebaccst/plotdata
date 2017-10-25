package br.inpe.ccst.eba.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;
import br.inpe.ccst.eba.repository.FamilyRepository;
import br.inpe.ccst.eba.repository.GenusRepository;
import br.inpe.ccst.eba.repository.SpeciesRepository;

public class TaxonomyServiceTest extends AbstractTest {
	@Autowired
	private TaxonomyService service;

	@MockBean
	private FamilyRepository familyRepositoryMock;

	@MockBean
	private GenusRepository genusRepositoryMock;

	@MockBean
	private SpeciesRepository speciesRepositoryMock;

	@Before
	public void setUp() {
		when(this.familyRepositoryMock.findByNameLike(DEFAULT_FAMILY_GENUS_NAME_LIKE))
				.thenReturn(getDefaultOptionsFamily());
		when(this.familyRepositoryMock.findByName(getDefaultOptionsFamily().get(0))).thenReturn(getDefaultFamily());

		when(this.genusRepositoryMock.findByNameLike(DEFAULT_FAMILY_GENUS_NAME_LIKE))
				.thenReturn(getDefaultOptionsGenus());
		when(this.genusRepositoryMock.findByName(getDefaultOptionsGenus().get(0))).thenReturn(getDefaultGenus());

		when(this.speciesRepositoryMock.findByNameLike(DEFAULT_SPECIES_NAME_LIKE))
				.thenReturn(getDefaultOptionsSpecies());
		when(this.speciesRepositoryMock.findByName(getDefaultOptionsSpecies().get(0))).thenReturn(getDefaultSpecies());
	}

	@Test
	public void shouldServiceNotBeNull() {
		assertNotNull(this.service);
	}

	@Test
	public void shouldGetSuggestionFamily() {
		Family sug = this.service.getSuggestionFamily(DEFAULT_FAMILY_GENUS_NAME_LIKE);

		assertThat(sug, is(not(nullValue())));
		assertThat(sug.getName(), is(equalTo("annonaceae")));
	}

	@Test
	public void shouldGetSuggestionGenus() {
		Genus sug = this.service.getSuggestionGenus(DEFAULT_FAMILY_GENUS_NAME_LIKE);

		assertThat(sug, is(not(nullValue())));
		assertThat(sug.getName(), is(equalTo("annona")));
	}

	@Test
	public void shouldGetSuggestionSpecies() {
		Species sug = this.service.getSuggestionSpecies(DEFAULT_SPECIES_NAME_LIKE);

		assertThat(sug, is(not(nullValue())));
		assertThat(sug.getName(), is(equalTo("antrocaryon nannanii")));
	}
}
