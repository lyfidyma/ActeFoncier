package com.cab.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responsable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResponsable;
	@NotBlank(message="Renseigner le nom")
	private String nomResponsable;
	@NotBlank(message="Renseigner le prénom")
	private String prenomResponsable;
	@NotBlank(message="Renseigner la fonction")
	private String fonction;
	
	public Responsable(String nomResponsable, String prenomResponsable, String fonction) {
		super();
		this.nomResponsable = nomResponsable;
		this.prenomResponsable = prenomResponsable;
		this.fonction = fonction;
	}

	
}
