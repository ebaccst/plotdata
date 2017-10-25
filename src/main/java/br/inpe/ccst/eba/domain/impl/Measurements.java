package br.inpe.ccst.eba.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.inpe.ccst.eba.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "measurements")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Measurements extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
    @JoinColumn(name = "owner_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;

    @JoinColumn(name = "information_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Information information;

    @JoinColumn(name = "geo_reference_id")
    @OneToOne(cascade = CascadeType.ALL)
    private GeoReference geoReference;

    @JoinColumn(name = "common_name_id")
    @OneToOne(cascade = CascadeType.ALL)
    private CommonName commonName;

    @JoinColumn(name = "family_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Family family;

    @JoinColumn(name = "genus_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Genus genus;

    @JoinColumn(name = "species_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Species species;

    @Column(name = "tree_id")
    private Integer treeId;

    @Column(name = "plot")
    private String plot;

    @Column(name = "year")
    private Integer year;

    @Column(name = "dap")
    private Double dap;

    @Column(name = "height")
    private Float height;

    @Column(name = "density")
    private Double density;

    @Column(name = "density_sd")
    private Double densitySD;

    @Column(name = "agb")
    private Double agb;
}
