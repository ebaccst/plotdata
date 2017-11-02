package br.inpe.ccst.eba.plot;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.inpe.ccst.eba.AbstractTest;
import br.inpe.ccst.eba.domain.impl.InformationHeight;

public class RecordTest extends AbstractTest {
	private static final String INPUT_PLOT = "JARAU_P06";
	private static final String OUTPUT_PLOT = "jarau_p06";

	@Test
	public void shouldBuildRecord() {
		Record spreadsheet = Record.builder()
				.plot(INPUT_PLOT)
				.build();

		assertThat(spreadsheet.getPlot(), is(equalTo(OUTPUT_PLOT)));
	}
	
	@Test
	public void shouldBuildRecordWithRecordNumber() {
		Record spreadsheet = Record.builder()
				.recordNumber(1L)
				.build();

		assertThat(spreadsheet.getRecordNumber(), is(equalTo(1L)));
	}
	
	@Test
	public void shouldBuildRecordWithYear() {
		Record spreadsheet = Record.builder()
				.year("2017")
				.build();

		assertThat(spreadsheet.getYear(), is(equalTo(2017)));
	}
	
	@Test
	public void shouldBuildRecordWithHeight() {
		Record spreadsheet = Record.builder()
				.height("1,76")
				.build();

		assertThat(spreadsheet.getHeight(), is(equalTo(1.76F)));
	}
	
	@Test
	public void shouldBuildRecordWithInformationHeight() {
		Record spreadsheet = Record.builder()
				.informationHeight("MED")
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(equalTo(InformationHeight.MEASURED)));
		
		spreadsheet = Record.builder()
				.informationHeight("EST")
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(equalTo(InformationHeight.ESTIMATED)));
		
		spreadsheet = Record.builder()
				.build();

		assertThat(spreadsheet.getInformationHeight(), is(nullValue()));
	}

	@Test
	public void shouldBuildRecordWithDAP() {
		Record spreadsheet = Record.builder()
				.dap("56.3")
				.build();

		assertThat(spreadsheet.getDap(), is(equalTo(56.3)));
	}
	
	@Test
	public void shouldBuildRecordWithTreeId() {
		Record spreadsheet = Record.builder()
				.treeId("13C")
				.build();

		assertThat(spreadsheet.getTreeId(), is(equalTo(13)));
		assertThat(spreadsheet.getInformationTreeId(), is(equalTo("c")));
		
		spreadsheet = Record.builder()
				.treeId("1")
				.build();

		assertThat(spreadsheet.getTreeId(), is(equalTo(1)));
		assertThat(spreadsheet.getInformationTreeId(), is(nullValue()));
	}
	
	@Test
	public void shouldBuildRecordWithInformationDead() {
		Record spreadsheet = Record.builder()
				.informationDead("NA")
				.build();

		assertThat(spreadsheet.getInformationDead(), is(equalTo(false)));
		
		spreadsheet = Record.builder()
				.informationDead("MORTA")
				.build();
		assertThat(spreadsheet.getInformationDead(), is(equalTo(true)));
	}
	
	@Test
	public void shouldBuildRecordWithHideExcelError() {
		Record spreadsheet = Record.builder()
				.plot("#VALOR!")
				.build();

		assertThat(spreadsheet.getPlot(), is(nullValue()));
	}
}
