package br.inpe.ccst.eba.plot;

import java.util.List;
import java.util.function.Consumer;

import br.inpe.ccst.eba.util.Parallel;
import br.inpe.ccst.eba.util.impl.ParallelImpl;
import lombok.Builder;
import lombok.Singular;

@Builder
public class Spreadsheet {
	@Singular
	private List<Record> records;

	private final Parallel parallel = new ParallelImpl();

	public int size() {
		return this.records.size();
	}

	public void each(Consumer<? super Record> action) {
		this.records.stream().forEachOrdered(action);
	}

	public void eachChunk(Integer numberOfThreads, Consumer<? super List<Record>> action) {
		if (numberOfThreads <= 1) {
			throw new IllegalArgumentException(String.format(
					"Argument 'numberOfThreads' should be an integer greater than one, got %d", numberOfThreads));
		}

		this.parallel.chunks(this.records, this.records.size() / numberOfThreads).forEach(action);
	}
}
