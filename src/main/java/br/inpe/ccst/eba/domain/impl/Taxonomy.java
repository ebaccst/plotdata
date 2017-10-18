package br.inpe.ccst.eba.domain.impl;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.inpe.ccst.eba.domain.CommonsFields;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Taxonomy extends CommonsFields{
	private static final long serialVersionUID = 1L;

    @JoinColumn(name = "family_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Family family;

    @JoinColumn(name = "genus_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Family genus;

    @JoinColumn(name = "species_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Family species;

    @JoinColumn(name = "density", nullable = false)
    private Double density;

    @JoinColumn(name = "region")
    private String region;

    @JoinColumn(name = "article_reference")
    private String articleReference;
}
