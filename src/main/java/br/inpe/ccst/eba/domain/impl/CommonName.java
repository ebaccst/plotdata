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

import br.inpe.ccst.eba.domain.CommonsFields;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "common_name")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonName extends CommonsFields {

	@Id
	@SequenceGenerator(name = "common_name_id_seq", sequenceName = "common_name_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_name_id_seq")
	@Column(name = "id", updatable = false)
    private Long id;

    @JoinColumn(name = "family_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Family family;

    @JoinColumn(name = "genus_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Genus genus;

    @JoinColumn(name = "species_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Species species;

    @Builder
	public CommonName(String name, Family family, Genus genus, Species species) {
		super(name);
		this.family = family;
		this.genus = genus;
		this.species = species;
	}
}
