package com.cab.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTypeDocument;
	//@Column(unique = true)
	@NotBlank(message="Choisir un type")
	private String typeDoc;
	
	@OneToMany(mappedBy = "typeDocument")
	private Collection<Documents> doc;
	
	public TypeDocument(String typeDoc) {
		super();
		this.typeDoc = typeDoc;
	}
	


	
}
