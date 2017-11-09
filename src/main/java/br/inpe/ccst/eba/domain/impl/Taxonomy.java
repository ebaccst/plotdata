package br.inpe.ccst.eba.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxonomy")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Taxonomy {

	@Id
	@SequenceGenerator(name = "taxonomy_id_seq", sequenceName = "taxonomy_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxonomy_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@JoinColumn(name = "family_id")
	@OneToOne(cascade = CascadeType.ALL)
	private Family family;

	@JoinColumn(name = "genus_id")
	@OneToOne(cascade = CascadeType.ALL)
	private Genus genus;

	@JoinColumn(name = "species_id")
	@OneToOne(cascade = CascadeType.ALL)
	private Species species;

	@JoinColumn(name = "density", nullable = false)
	private Double density;

	@JoinColumn(name = "region")
	private String region;

	@JoinColumn(name = "article_reference")
	private String articleReference;
}
