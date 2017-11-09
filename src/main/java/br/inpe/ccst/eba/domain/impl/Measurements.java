package br.inpe.ccst.eba.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
@EqualsAndHashCode
public class Measurements {
	
	@Id
	@SequenceGenerator(name = "measurements_id_seq", sequenceName = "measurements_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurements_id_seq")
	@Column(name = "id", updatable = false)
    private Long id;
	
	@JoinColumn(name = "information_id")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Information information;
	
    @JoinColumn(name = "plot_id")
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Plot plot;

    @JoinColumn(name = "geo_reference_id")
    @OneToOne(cascade = CascadeType.ALL)
    private GeoReference geoReference;

    @JoinColumn(name = "common_name_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private CommonName commonName;

    @JoinColumn(name = "family_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Family family;

    @JoinColumn(name = "genus_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Genus genus;

    @JoinColumn(name = "species_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Species species;

    @Column(name = "tree_id")
    private Integer treeId;

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
    
    @Override
	public String toString() {
		try {
			return new ObjectMapper()
					.writer()
					.withDefaultPrettyPrinter()
					.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
