package com.cab.sn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("ENTREPRISE")
//@PrimaryKeyJoinColumn(name = "idEntreprise")
public class Entreprise extends Beneficiaire{
	
	private String nomEntreprise;
	@Column(unique=true)
	private String ninea;

}
