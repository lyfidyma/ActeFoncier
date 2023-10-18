package com.cab.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	private String libelleCommune;
	@OneToOne
	@JoinColumn(name="idCommuneArrond", updatable = true)
	private CommuneArrondissement communeArrondissement;
	public Commune(String libelleCommune, CommuneArrondissement communeArrondissement) {
		super();
		this.libelleCommune = libelleCommune;
		this.communeArrondissement = communeArrondissement;
	}
	
	
	

}
