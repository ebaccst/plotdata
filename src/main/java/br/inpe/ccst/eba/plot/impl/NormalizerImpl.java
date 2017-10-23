package br.inpe.ccst.eba.plot.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import br.inpe.ccst.eba.plot.Normalizer;

@Configuration("normalizer")
public class NormalizerImpl implements Normalizer {
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String SINGLE_QUOTATION_MARKS = "'";
	private static final String DOUBLE_QUOTATION_MARKS = "\"";
	private static final String HYPHEN = "-";
	private static final String COMMA = ",";
	private static final String DOT = ".";

	private static final List<String> PREPOSITIONS_PT = Arrays.asList("da", "das", "de", "do", "dos");

	private static final String PATTERN_SPACES = "\\s+";
	private static final String PATTERN_TAB = "\\t+";
	private static final String PATTERN_ACCENT = "[\\p{InCombiningDiacriticalMarks}]";

	@Override
	public String apply(String string, boolean joinWords) {
		String value = toUTF8(string);
		value = value.toLowerCase().trim();
		value = value.replaceAll(SINGLE_QUOTATION_MARKS, EMPTY);
		value = value.replaceAll(DOUBLE_QUOTATION_MARKS, EMPTY);
		value = value.replaceAll(HYPHEN, SPACE);
		value = value.replaceAll(PATTERN_TAB, SPACE);
		value = value.replaceAll(PATTERN_SPACES, SPACE);
		value = value.replaceAll(COMMA, DOT);

		List<String> values = new ArrayList<>(Arrays.asList(value.split(SPACE)));
		if (!values.isEmpty()) {
			for (int i = 0; i < values.size(); i++) {
				if (PREPOSITIONS_PT.contains(values.get(i))) {
					values.remove(i);
				}
			}

			value = String.join(SPACE, values);
		}

		if (joinWords) {
			value = value.replaceAll(SPACE, EMPTY);
		}

		return value;
	}

	@Override
	public String apply(String string) {
		return apply(string, false);
	}

	private String toUTF8(String string) {
		String normalized = java.text.Normalizer.normalize(string, java.text.Normalizer.Form.NFD);
		return normalized.replaceAll(PATTERN_ACCENT, EMPTY);
	}
}
