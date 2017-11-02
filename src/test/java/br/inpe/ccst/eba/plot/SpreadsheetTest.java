package br.inpe.ccst.eba.plot;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.plot.Spreadsheet.SpreadsheetBuilder;

public class SpreadsheetTest extends AbstractTest {
	private Record record;

	@Before
	public void setUp() {
		this.record = Record.builder().commonName("test").build();
	}

	@After
	public void tearDown() {
		this.record = null;
	}

	@Test
	public void shouldBuildSpreadsheet() {
		SpreadsheetBuilder builder = Spreadsheet.builder();

		builder.record(record);
		Spreadsheet spreadsheet = builder.build();
		assertThat(spreadsheet.size(), is(equalTo(1)));

		builder.clearRecords();
		spreadsheet = builder.build();
		assertThat(spreadsheet.size(), is(equalTo(0)));
	}

	@Test
	public void shouldIterateOverRecords() {
		Spreadsheet spreadsheet = Spreadsheet.builder().record(record).build();
		
		spreadsheet.each(rec -> {
			assertThat(rec.getCommonName(), is(equalTo("test")));
		});
	}

}
