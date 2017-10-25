package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;

import br.inpe.ccst.eba.domain.AbstractEntity;
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
@EqualsAndHashCode(callSuper = true)
public class GeoReference extends AbstractEntity {
	private static final long serialVersionUID = 1L;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @Column(name = "az")
    private Double az;

    @Column(name = "azimuth")
    private Float azimuth;

    @Column(name = "geom", nullable = false, columnDefinition = "geometry(Point,5880)")
    private Point geom;
}
