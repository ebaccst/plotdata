package br.inpe.ccst.eba.plot;

public enum OwnerHeader {
	PLOT(0), TRANSECT(1), OWNER(2), INSTITUTION(3);

	private final int column;

	OwnerHeader(int column) {
		this.column = column;
	}
	
	public int get() {
		return this.column;
	}	
}
