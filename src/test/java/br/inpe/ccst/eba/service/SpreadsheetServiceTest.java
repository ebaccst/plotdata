package br.inpe.ccst.eba.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;

public class SpreadsheetServiceTest extends AbstractTest {
	
	@Autowired
	private SpreadsheetService service;
	
	@MockBean
	private SpreadsheetValidator validator;
	
	private Spreadsheet input;
	
	@Before
	public void setUp() {
		this.input = Spreadsheet.builder().build();

		Map<Long, String> errors = new HashMap<>();
		errors.put(1L, "Family 'test' not found in family table.");
		errors.put(2L, "Genus 'test' not found in genus table.");
		errors.put(3L, "Species 'test' not found in species table.");
		errors.put(4L, "Common name 'test' not found in common_name table.");

		errors.put(5L, "Family 'ann' not found in family table. Do you mean 'annonaceae'?");
		errors.put(6L, "Genus 'ann' not found in genus table. Do you mean 'annona'?");
		errors.put(7L, "Species 'anna' not found in species table. Do you mean 'antrocaryon nannanii'?");
		errors.put(8L, "Common name 'birib' not found in common_name table. Do you mean 'biriba'?");

		errors.put(9L, "Family 'test', Genus 'test', Species 'test' and Common name 'test' not found in database.");
		
		when(this.validator.validate(input)).thenReturn(errors);
	}
	
	@After
	public void tearDown() {
		this.input = null;
	}
	
	@Test
	public void shouldServiceNotBeNull() {
		assertThat(this.service, is(not(nullValue())));
	}
	
	@Test
	public void shouldValidateSpreadsheet() {
		Map<Long, String> errors = this.service.validate(input);
		
		assertThat(errors.size(), is(equalTo(9)));
		assertThat(errors.get(1L), is(equalTo("Family 'test' not found in family table.")));
		assertThat(errors.get(2L), is(equalTo("Genus 'test' not found in genus table.")));
		assertThat(errors.get(3L), is(equalTo("Species 'test' not found in species table.")));
		assertThat(errors.get(4L), is(equalTo("Common name 'test' not found in common_name table.")));
		assertThat(errors.get(5L), is(equalTo("Family 'ann' not found in family table. Do you mean 'annonaceae'?")));
		assertThat(errors.get(6L), is(equalTo("Genus 'ann' not found in genus table. Do you mean 'annona'?")));
		assertThat(errors.get(7L), is(equalTo("Species 'anna' not found in species table. Do you mean 'antrocaryon nannanii'?")));
		assertThat(errors.get(8L), is(equalTo("Common name 'birib' not found in common_name table. Do you mean 'biriba'?")));
		assertThat(errors.get(9L), is(equalTo("Family 'test', Genus 'test', Species 'test' and Common name 'test' not found in database.")));
	}
}
