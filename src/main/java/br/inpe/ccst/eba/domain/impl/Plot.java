package br.inpe.ccst.eba.domain.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Geometry;

import br.inpe.ccst.eba.domain.CommonsFields;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plot")
@Getter
@Setter
@NoArgsConstructor
public class Plot extends CommonsFields {
	
	@Id
	@SequenceGenerator(name = "plot_id_seq", sequenceName = "plot_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plot_id_seq")
	@Column(name = "id", updatable = false)
    private Long id;

	@JoinColumn(name = "owner_id")
	@OneToOne(cascade = CascadeType.MERGE)
	private Owner owner;
	
	@Column(name = "observation")
    private String observation;
	
	@Column(name = "transect")
	private String transect;
	
	@Column(name = "geom", nullable = true, columnDefinition = "geometry(Geometry)")
    private Geometry geom;
	
	@OneToMany(mappedBy = "plot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Measurements> measurements;

	@Builder
	public Plot(String name, Owner owner, String observation, String transect, Geometry geom) {
		super(name);
		this.owner = owner;
		this.observation = observation;
		this.transect = transect;
		this.geom = geom;
	}
}