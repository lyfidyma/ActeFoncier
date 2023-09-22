package com.cab.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLocalisation;
	private String commune;
	private String communeArrond;
	private String departement;
	private String region;
	
	public Localisation(String commune, String communeArrond, String departement, String region) {
		super();
		this.commune = commune;
		this.communeArrond = communeArrond;
		this.departement = departement;
		this.region = region;
	}
	
	
}
