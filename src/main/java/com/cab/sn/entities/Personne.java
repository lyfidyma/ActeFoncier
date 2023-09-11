package com.cab.sn.entities;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("PERSONNE")
//@PrimaryKeyJoinColumn(name = "idPersonne")
public class Personne extends Beneficiaire{
	
	@Column(unique=true)
	private Long cni;
	private String nomPersonne;
	private String prenom;
	@Column(unique=true)
	private Long nin;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dateDelivrance ;

}
