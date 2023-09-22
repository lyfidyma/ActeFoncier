package com.cab.sn.entities;

import jakarta.persistence.Entity;
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
//@DiscriminatorValue("ENTREPRISE")
//@PrimaryKeyJoinColumn(name = "idEntreprise")
public class Entreprise extends Beneficiaire{
	
	private String nomEntreprise;
	//@Column(unique=true)
	private String ninea;
	@OneToOne
	private Personne personne;
	public Entreprise(String nomEntreprise, String ninea) {
		super();
		this.nomEntreprise = nomEntreprise;
		this.ninea = ninea;
	}
	
	

}
