package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.inpe.ccst.eba.domain.CommonsFields;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "family")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Family extends CommonsFields{

	@Id
	@SequenceGenerator(name = "family_id_seq", sequenceName = "family_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "family_id_seq")
	@Column(name = "id", updatable = false)
    private Long id;

	@Builder
	public Family(String name) {
		super(name);
	}
}
