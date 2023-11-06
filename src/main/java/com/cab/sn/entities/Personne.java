package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
public class Personne{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPersonne;
	@Column(nullable=false)
	@Pattern(regexp= "^[1-2]\\d{12}$", message="La CNI doit comporter 13 chiffres")
	private String cni;
	@Column(nullable=false)
	@NotBlank(message="Renseigner le nom")
	private String nomPersonne;
	@Column(nullable=false)
	@NotBlank(message="Renseigner le prénom")
	private String prenom;
	@Column(nullable=true)
	@Pattern(regexp= "^[1-2]\\d{16}$", message="Le numéro CEDEAO doit comporter 17 chiffres")
	private String nin;
	
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private LocalDate dateDelivrance ;
	
	public Personne(String cni, String nomPersonne, String prenom, String nin, LocalDate dateDelivrance) {
		super();
		this.cni = cni;
		this.nomPersonne = nomPersonne;
		this.prenom = prenom;
		this.nin = nin;
		this.dateDelivrance = dateDelivrance;
	}
	
	
	
	

}
