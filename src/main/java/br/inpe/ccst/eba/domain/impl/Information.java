package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.inpe.ccst.eba.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "information")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Information extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
    @Column(name = "plot")
    private String plot;

    @Column(name = "height")
    @Enumerated(EnumType.STRING)
    private InformationHeight height;

    @Column(name = "dead")
    private String dead;

    @Column(name = "type")
    private String type;

    @Column(name = "density")
    private String density;
}
