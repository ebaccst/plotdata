package br.inpe.ccst.eba.service;

import java.util.List;

public interface SuggestionService {
	String getBestMatch(String argument, List<String> options);
}
