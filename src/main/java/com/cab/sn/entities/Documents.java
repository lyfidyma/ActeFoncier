package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
public class Documents {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocument;
	@NotEmpty(message="Renseigner le numéro du document")
	//@Size(min=1, max=250)
	private String numDocument;
	//@NotEmpty(message="Renseigner la date du document")
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private LocalDate dateDocument;
	private Date dateSysteme= new Date();
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private LocalDate dateCreation ;
	@NotEmpty(message="Renseigner le titre")
	private String titreGlobal;
	
	private String objetDocument;
	private String statutDocument;
	
	private String typeBeneficiaire;
	private String responsableDocument;
	@NotNull(message="Renseigner le lot")
	@Min(value=1)
	private int lot;
	@NotEmpty(message="Renseigner le nicad")
	private String nicad;
	//@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private LocalDate dateApprobation;
	@NotNull(message="Renseigner la superficie")
	@Min(value=1)
	private int superficie;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idBeneficiaire")
	private Beneficiaire beneficiaire;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="idLocalisation", updatable = true)
	private Localisation localisation;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="idTypeDocument", updatable = true)
	private TypeDocument typeDocument;
	@OneToMany(mappedBy = "documents", cascade = CascadeType.ALL)
	private Collection<PiecesJointes> piecesJointes;
	public Documents(String numDocument, LocalDate dateDocument, LocalDate dateCreation, String titreGlobal, String objetDocument,
			String statutDocument, String typeBeneficiaire, String responsableDocument, int lot, String nicad,
			LocalDate dateApprobation, int superficie, Beneficiaire beneficiaire, Localisation localisation,
			TypeDocument typeDocument) {
		super();
		this.numDocument = numDocument;
		this.dateDocument = dateDocument;
		this.dateCreation = dateCreation;
		this.titreGlobal = titreGlobal;
		this.objetDocument = objetDocument;
		this.statutDocument = statutDocument;
		this.typeBeneficiaire = typeBeneficiaire;
		this.responsableDocument = responsableDocument;
		this.lot = lot;
		this.nicad = nicad;
		this.dateApprobation = dateApprobation;
		this.superficie = superficie;
		this.beneficiaire = beneficiaire;
		this.localisation = localisation;
		this.typeDocument = typeDocument;
	}
	public Documents(String numDocument, LocalDate dateDocument, LocalDate dateCreation, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, int lot,
			String nicad, LocalDate dateApprobation, int superficie, Beneficiaire beneficiaire,
			Localisation localisation, TypeDocument typeDocument, Collection<PiecesJointes> piecesJointes) {
		super();
		this.numDocument = numDocument;
		this.dateDocument = dateDocument;
		this.dateCreation = dateCreation;
		this.titreGlobal = titreGlobal;
		this.objetDocument = objetDocument;
		this.statutDocument = statutDocument;
		this.typeBeneficiaire = typeBeneficiaire;
		this.responsableDocument = responsableDocument;
		this.lot = lot;
		this.nicad = nicad;
		this.dateApprobation = dateApprobation;
		this.superficie = superficie;
		this.beneficiaire = beneficiaire;
		this.localisation = localisation;
		this.typeDocument = typeDocument;
		this.piecesJointes = piecesJointes;
	}

	

}
