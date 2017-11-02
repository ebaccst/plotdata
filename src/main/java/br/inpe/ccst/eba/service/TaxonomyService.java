package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;

public interface TaxonomyService {
	
	Family findFamily(String family);

	Genus findGenus(String genus);

	Species findSpecies(String species);

	Family getSuggestionFamily(String name);

	Genus getSuggestionGenus(String name);

	Species getSuggestionSpecies(String name);
}
