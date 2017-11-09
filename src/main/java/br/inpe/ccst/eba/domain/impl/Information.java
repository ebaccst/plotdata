package br.inpe.ccst.eba.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@EqualsAndHashCode
public class Information {
	
	@Id
	@SequenceGenerator(name = "information_id_seq", sequenceName = "information_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "information_id_seq")
	@Column(name = "id")
    private Long id;

    @Column(name = "height")
    @Enumerated(EnumType.STRING)
    private InformationHeight height;
    
    @Column(name = "tree_id")
    private String treeId;

    @Column(name = "dead")
    private Boolean dead;

    @Column(name = "type")
    private String type;

    @Column(name = "density")
    private String density;
}
