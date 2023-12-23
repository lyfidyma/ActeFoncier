package com.cab.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Commune {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommune;
	@NotBlank(message="Renseigner la commune")
	private String libelleCommune;
	@ManyToOne
	@JoinColumn(name="idCommuneArrond", updatable = true)
	private CommuneArrondissement communeArrondissement;
	
	@OneToMany(mappedBy = "commune")
	private Collection<Documents> doc;
	
	public Commune(String libelleCommune, CommuneArrondissement communeArrondissement) {
		super();
		this.libelleCommune = libelleCommune;
		this.communeArrondissement = communeArrondissement;
	}
	
	
	

}
