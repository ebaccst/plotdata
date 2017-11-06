package br.inpe.ccst.eba.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.plot.Record;
import br.inpe.ccst.eba.plot.Spreadsheet;

public class SpreadsheetValidatorTest extends AbstractTest {

	@Autowired
	@Qualifier("suggestionValidator")
	private SpreadsheetValidator suggestionValidator;
	
	@Autowired
	@Qualifier("globalNamesResolverValidator")
	private SpreadsheetValidator globalNameResolverValidator;

	private Record record;

	@Before
	public void setUp() {
		this.record = getDefaultRecord();
	}

	@After
	public void tearDown() {
		this.record = null;
	}

	@Test
	public void shouldSuggestionValidatorNotBeNull() {
		assertThat(this.suggestionValidator, is(not(nullValue())));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithNoError() {
		Spreadsheet input = Spreadsheet.builder().record(record).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(0));
	}
	
	@Test
	public void shouldValidateSpreedshetUsingGlobalNamesResolverWithNoError() {
		Spreadsheet input = Spreadsheet.builder().record(record).build();
		Map<Long, String> errors = this.globalNameResolverValidator.validate(input);
		assertThat(errors.size(), is(0));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithSpeciesError() {
		Record columnError = Record.builder().recordNumber(1L).species(DEFAULT_SPECIES_NAME_LIKE)
				.genus(this.record.getGenus()).family(this.record.getFamily()).commonName(this.record.getCommonName())
				.build();

		Spreadsheet input = Spreadsheet.builder().record(columnError).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(1));
		assertThat(errors.get(1L), is("Species 'anna' not found in database. Do you mean 'antrocaryon nannanii'?"));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithGenusError() {
		Record columnError = Record.builder().recordNumber(1L).genus(DEFAULT_FAMILY_GENUS_NAME_LIKE)
				.family(this.record.getFamily()).commonName(this.record.getCommonName()).build();

		Spreadsheet input = Spreadsheet.builder().record(columnError).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(1));
		assertThat(errors.get(1L), is("Genus 'ann' not found in database. Do you mean 'lannea'?"));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithFamilyError() {
		Record columnError = Record.builder().recordNumber(1L).genus(this.record.getGenus())
				.family(DEFAULT_FAMILY_GENUS_NAME_LIKE).commonName(this.record.getCommonName()).build();

		Spreadsheet input = Spreadsheet.builder().record(columnError).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(1));
		assertThat(errors.get(1L), is("Family 'ann' not found in database. Do you mean 'annonaceae'?"));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithCommonNameError() {
		Record columnError = Record.builder().recordNumber(1L).genus(this.record.getGenus())
				.family(this.record.getFamily()).commonName(DEFAULT_COMMON_NAME_SIMILAR).build();

		Spreadsheet input = Spreadsheet.builder().record(columnError).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(1));
		assertThat(errors.get(1L), is("Common name 'birib' not found in database. Do you mean 'biriba'?"));
	}

	@Test
	public void shouldValidateSpreedshetUsingSuggestionWithTableError() {
		Record columnError = Record.builder().recordNumber(1L).species("test").genus(DEFAULT_FAMILY_GENUS_NAME_LIKE)
				.family(DEFAULT_FAMILY_GENUS_NAME_LIKE).commonName(DEFAULT_COMMON_NAME_SIMILAR).build();

		Spreadsheet input = Spreadsheet.builder().record(columnError).build();
		Map<Long, String> errors = this.suggestionValidator.validate(input);
		assertThat(errors.size(), is(1));
		assertThat(errors.get(1L),
				is("Family 'ann', Genus 'ann', Species 'test' and Common name 'birib' not found in database."));
	}
}
