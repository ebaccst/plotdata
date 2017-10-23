package br.inpe.ccst.eba.plot;

public enum SpreadsheetHeader {
	PLOT(0), INFORMATION_PLOT(1), YEAR(2), TREE_ID(3), COMMON_NAME(4), FAMILY(5), GENUS(6), SPECIES(7), HEIGHT(8), INFORMATION_HEIGHT(9), DAP(10), INFORMATION_DEAD(11), INFORMATION_TYPE(12);

	private final int column;

	SpreadsheetHeader(int column) {
		this.column = column;
	}
	
	public int get() {
		return this.column;
	}	
}
