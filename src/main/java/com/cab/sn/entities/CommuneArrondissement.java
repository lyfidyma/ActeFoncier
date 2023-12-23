package com.cab.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

public class CommuneArrondissement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCommuneArrond;
	@NotBlank(message="Entrer une commune existante")
	private String libelleCommuneArrond;
	
	@OneToMany(mappedBy = "communeArrondissement")
	private Collection<Commune> commune;
	
	@ManyToOne
	@JoinColumn(name="idDepartement", updatable = true)
	private Departement departement;
	
	public CommuneArrondissement(String libelleCommuneArrond, Departement departement) {
		super();
		this.libelleCommuneArrond = libelleCommuneArrond;
		this.departement = departement;
	}
	
	
	
	

}
