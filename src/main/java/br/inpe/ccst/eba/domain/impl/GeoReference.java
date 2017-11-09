package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_reference")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GeoReference {
	
	@Id
	@SequenceGenerator(name = "geo_reference_id_seq", sequenceName = "geo_reference_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_reference_id_seq")
	@Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @Column(name = "az")
    private Double az;

    @Column(name = "azimuth")
    private Float azimuth;

    @Column(name = "geom", nullable = true, columnDefinition = "geometry(Point, 5880)")
    private Point geom;
}
