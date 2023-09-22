package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("PERSONNE")
//@PrimaryKeyJoinColumn(name = "idPersonne")
public class Personne extends Beneficiaire{
	
	
	//@Pattern(regexp="^[1-2]\\{12}$", message="Renseigner la cni au bon format")
	private Long cni;
	private String nomPersonne;
	private String prenom;
	//@Column(unique=true)
	private Long nin;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private LocalDate dateDelivrance ;
	@OneToMany
	private Collection<Entreprise> entreprise;
	public Personne(Long cni, String nomPersonne, String prenom, Long nin, LocalDate dateDelivrance) {
		super();
		this.cni = cni;
		this.nomPersonne = nomPersonne;
		this.prenom = prenom;
		this.nin = nin;
		this.dateDelivrance = dateDelivrance;
	}
	
	
	
	

}
