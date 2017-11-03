package br.inpe.ccst.eba.util;

import java.util.List;

public interface Parallel {

	<T> List<List<T>> chunks(List<T> elements, Integer chunkSize);

}