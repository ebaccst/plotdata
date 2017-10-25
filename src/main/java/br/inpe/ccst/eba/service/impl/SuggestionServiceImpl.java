package br.inpe.ccst.eba.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import br.inpe.ccst.eba.service.SuggestionService;

@Service("suggestionService")
public class SuggestionServiceImpl implements SuggestionService {

	private static final LevenshteinDistance UNLIMITED_DISTANCE = new LevenshteinDistance();
	private static final String WORLD_DEFAULT = "";
	private static final Double PERCENTAGE_OF_DISTANCE = 0.6;

	@Override
	public String getBestMatch(String argument, List<String> options) {
		final Integer argumentDistance = argument.length();
		final Double bestDistance = argumentDistance * PERCENTAGE_OF_DISTANCE;

		String word = WORLD_DEFAULT;
		Integer distance = argumentDistance;
		Map<String, Integer> possibleResults = new HashMap<>();
		for (String option : options) {
			if (option.equals(argument)) {
				return option; 
			}
			
			Integer currentDistance = UNLIMITED_DISTANCE.apply(argument, option);
			if (option.contains(argument)) {
				possibleResults.put(option, currentDistance);
			}

			if (currentDistance < distance) {
				distance = currentDistance;
				word = option;
			}
		}

		if (distance < bestDistance) {
			return word;
		}

		Optional<Entry<String, Integer>> bestAlternative = possibleResults
				.entrySet()
				.stream()
				.min(Map.Entry.comparingByValue());
		if (bestAlternative.isPresent()) {
			return bestAlternative.get().getKey();
		}

		return WORLD_DEFAULT;
	}

}
