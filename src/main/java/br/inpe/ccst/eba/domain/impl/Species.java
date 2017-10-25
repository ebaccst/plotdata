package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.inpe.ccst.eba.domain.CommonsFields;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "species")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Species extends CommonsFields{
	private static final long serialVersionUID = 1L;

	@Builder
	public Species(String name) {
		super(name);
	}	
}
