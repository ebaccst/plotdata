package br.inpe.ccst.eba.plot;

import java.util.Set;
import java.util.function.Consumer;

import lombok.Builder;
import lombok.Singular;

@Builder
public class Spreadsheet {
	@Singular
	private Set<Record> records;
	
	public int size() {
		return this.records.size();
	}
	
	public void each(Consumer<? super Record> action) {
		this.records.stream().forEachOrdered(action);
	}
}
