package com.cab.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDepartement;
	private String libelleDepartement;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idRegion", updatable = true)

	private Region region;
	public Departement(String libelleDepartement, Region region) {
		super();
		this.libelleDepartement = libelleDepartement;
		this.region = region;
	}
	public Departement(String libelleDepartement) {
		super();
		this.libelleDepartement = libelleDepartement;
	}
	
	
	
	
	
	

}
