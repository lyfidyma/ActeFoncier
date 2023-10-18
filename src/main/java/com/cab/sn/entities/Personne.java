package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	
	@Column(nullable=false)
	@NotNull(message="Renseigner la cni")
	private Long cni;
	@Column(nullable=false)
	@NotBlank(message="Renseigner le nom")
	private String nomPersonne;
	@Column(nullable=false)
	@NotBlank(message="Renseigner le prénom")
	private String prenom;
	@Column(nullable=true)
	
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
