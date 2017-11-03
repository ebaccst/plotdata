package br.inpe.ccst.eba.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.inpe.ccst.eba.AbstractTest;

public class ParallelTest extends AbstractTest {

	@Autowired
	private Parallel parallel;

	private List<Integer> elements;

	@Before
	public void setUp() {
		this.elements = new ArrayList<>();
		for (int i = 0; i < 52; i++)
			this.elements.add(i % 2);
		Collections.shuffle(elements);
	}

	@After
	public void tearDown() {
		this.elements = null;
	}

	@Test
	public void shouldParallelNotBeNull() {
		assertThat(this.parallel, is(not(nullValue())));
	}

	@Test
	public void shouldCreateChunks() {
		List<List<Integer>> chunks = parallel.chunks(this.elements, 10);

		assertThat(chunks.size(), is(6));
		assertThat(chunks.get(0).size(), is(10));
		assertThat(chunks.get(4).size(), is(10));
		assertThat(chunks.get(5).size(), is(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExpeceptionWhenElementsIsEmpty() {
		this.elements = new ArrayList<>();
		parallel.chunks(this.elements, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExpeceptionWhenChunksSizeIsLessThanEqualTo1() {
		parallel.chunks(this.elements, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExpeceptionWhenElementsSizeIsLessThanChunksSize() {
		parallel.chunks(this.elements, this.elements.size() + 1);
	}

}
