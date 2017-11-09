package br.inpe.ccst.eba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inpe.ccst.eba.domain.impl.Family;
import br.inpe.ccst.eba.domain.impl.Genus;
import br.inpe.ccst.eba.domain.impl.Species;
import br.inpe.ccst.eba.repository.FamilyRepository;
import br.inpe.ccst.eba.repository.GenusRepository;
import br.inpe.ccst.eba.repository.SpeciesRepository;
import br.inpe.ccst.eba.service.SuggestionService;
import br.inpe.ccst.eba.service.TaxonomyService;

@Service("taxonomyService")
public class TaxonomyServiceImpl implements TaxonomyService {
	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private GenusRepository genusRepository;

	@Autowired
	private SpeciesRepository speciesRepository;

	@Autowired
	private SuggestionService suggestion;

	@Override
	public synchronized Family findFamily(String family) {
		return this.familyRepository.findByName(family);
	}

	@Override
	public synchronized Genus findGenus(String genus) {
		return this.genusRepository.findByName(genus);
	}

	@Override
	public synchronized Species findSpecies(String species) {
		return this.speciesRepository.findByName(species);
	}

	@Override
	public synchronized Family getSuggestionFamily(String name) {
		List<String> options = this.familyRepository.findByNameLike(name);
		String sug = this.suggestion.getBestMatch(name, options);
		return this.findFamily(sug);
	}

	@Override
	public synchronized Genus getSuggestionGenus(String name) {
		List<String> options = this.genusRepository.findByNameLike(name);
		String sug = this.suggestion.getBestMatch(name, options);
		return this.findGenus(sug);
	}

	@Override
	public synchronized Species getSuggestionSpecies(String name) {
		List<String> options = this.speciesRepository.findByNameLike(name);
		String sug = this.suggestion.getBestMatch(name, options);
		return this.findSpecies(sug);
	}

	@Override
	@Transactional
	public Family saveFamily(String name) {
		Family family = Family.builder()
			.name(name)
			.build();

		return this.familyRepository.save(family);
	}

	@Override
	@Transactional
	public Genus saveGenus(String name) {
		Genus genus = Genus.builder()
			.name(name)
			.build();
		
		return this.genusRepository.save(genus);
	}

	@Override
	@Transactional
	public Species saveSpecies(String name) {
		Species species = Species.builder()
			.name(name)
			.build();

		return this.speciesRepository.save(species);
	}

}
