package br.inpe.ccst.eba.plot;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.InformationHeight;

public class SpreadsheetTest extends AbstractTest {
	private static final String INPUT_PLOT = "JARAU_P06";
	private static final String OUTPUT_PLOT = "jarau_p06";

	@Test
	public void shouldBuildSpreadsheet() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.plot(INPUT_PLOT)
				.build();

		assertThat(spreadsheet.getPlot(), is(equalTo(OUTPUT_PLOT)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithRecordNumber() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.recordNumber(1L)
				.build();

		assertThat(spreadsheet.getRecordNumber(), is(equalTo(1L)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithYear() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.year("2017")
				.build();

		assertThat(spreadsheet.getYear(), is(equalTo(2017)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithHeight() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.height("1,76")
				.build();

		assertThat(spreadsheet.getHeight(), is(equalTo(1.76F)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithInformationHeight() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.informationHeight("MED")
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(equalTo(InformationHeight.MEASURED)));
		
		spreadsheet = Spreadsheet.builder()
				.informationHeight("EST")
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(equalTo(InformationHeight.ESTIMATED)));
		
		spreadsheet = Spreadsheet.builder()
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(nullValue()));
	}

	@Test
	public void shouldBuildSpreadsheetWithDAP() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.dap("56.3")
				.build();

		assertThat(spreadsheet.getDap(), is(equalTo(56.3)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithTreeId() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.treeId("13C")
				.build();

		assertThat(spreadsheet.getTreeId(), is(equalTo(13)));
		assertThat(spreadsheet.getInformationTreeId(), is(equalTo("c")));
		
		spreadsheet = Spreadsheet.builder()
				.treeId("1")
				.build();

		assertThat(spreadsheet.getTreeId(), is(equalTo(1)));
		assertThat(spreadsheet.getInformationTreeId(), is(nullValue()));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithInformationDead() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.informationDead("NA")
				.build();

		assertThat(spreadsheet.getInformationDead(), is(equalTo(false)));
		
		spreadsheet = Spreadsheet.builder()
				.informationDead("MORTA")
				.build();
		assertThat(spreadsheet.getInformationDead(), is(equalTo(true)));
	}
	
	@Test
	public void shouldBuildSpreadsheetWithHideExcelError() {
		Spreadsheet spreadsheet = Spreadsheet.builder()
				.plot("#VALOR!")
				.build();

		assertThat(spreadsheet.getPlot(), is(nullValue()));
	}
}
