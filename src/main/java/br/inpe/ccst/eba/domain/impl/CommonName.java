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
@Table(name = "common_name")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommonName extends CommonsFields {
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
}
