package br.inpe.ccst.eba.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class CommonsFields extends AbstractEntity{
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false)
    private String name;
}
