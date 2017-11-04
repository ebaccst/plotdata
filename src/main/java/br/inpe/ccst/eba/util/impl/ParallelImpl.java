package br.inpe.ccst.eba.util.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.inpe.ccst.eba.util.Parallel;

@Component("parallel")
public class ParallelImpl implements Parallel {

	@Override
	public <T> List<List<T>> chunks(List<T> elements, Integer chunkSize) {
		if (elements.isEmpty()) {
			throw new IllegalArgumentException("Argument 'elements' is empty.");
		}

		final int min = 1;
		if (chunkSize <= min) {
			throw new IllegalArgumentException(
					String.format("Argument 'chunkSize' should be an integer greater than 1, got %d.", chunkSize));
		}

		final int size = elements.size();
		if (chunkSize >= size) {
			throw new IllegalArgumentException(
					String.format("Argument 'chunkSize' should be an integer less than %d, got %d.", size, chunkSize));
		}

		int numberOfLists = size / chunkSize;
		if (size % chunkSize != 0) {
			numberOfLists++;
		}

		List<List<T>> chunks = new ArrayList<>(numberOfLists);
		int statswith = 0;
		for (int i = 0; i < numberOfLists; i++) {
			int endswith = statswith + chunkSize;
			int internalSize = chunkSize + min;
			if (size < endswith) {
				endswith = size;
				internalSize = (endswith - statswith) + min;
			}

			List<T> chunk = new ArrayList<>(internalSize);
			for (int j = statswith; j < endswith; j++) {
				chunk.add(elements.get(j));
			}

			chunks.add(chunk);
			statswith = endswith;
		}

		return chunks;
	}
}
