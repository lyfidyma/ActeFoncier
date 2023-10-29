package com.cab.sn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

public class Entreprise{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idEntreprise;
	@Column(nullable=false)
	private String nomEntreprise;
	@Column(nullable=false)
	private String ninea;
	
	public Entreprise(String nomEntreprise, String ninea) {
		super();
		this.nomEntreprise = nomEntreprise;
		this.ninea = ninea;
	}
	
	

}
