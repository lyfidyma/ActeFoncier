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
import jakarta.validation.constraints.NotEmpty;
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
	private String numDocument;
	private String codeUniqueDocument;
	//@NotEmpty(message="Renseigner la date du document")
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private LocalDate dateDocument;
	//private Date dateSysteme= new Date();
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private Date dateCreation = new Date() ;
	@NotEmpty(message="Renseigner le titre")
	private String titreGlobal;
	
	private String objetDocument;
	private String statutDocument;
	
	private String typeBeneficiaire;
	private String responsableDocument;
	@NotEmpty(message="Renseigner le lot")
	private String lot;
	@NotEmpty(message="Renseigner le nicad")
	private String nicad;
	//@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private Date dateApprobation;
	@NotEmpty(message="Renseigner la superficie")
	private String superficie;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idBeneficiaire")
	private Beneficiaire beneficiaire;
	@OneToOne
	@JoinColumn(name="idCommune", updatable = true)
	private Commune commune;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="idLocalisation", updatable = true)
	private Localisation localisation;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idTypeDocument", updatable = true)
	private TypeDocument typeDocument;
	@OneToMany(mappedBy = "documents", cascade = CascadeType.ALL)
	private Collection<PiecesJointes> piecesJointes;
	public Documents(@NotEmpty(message = "Renseigner le numéro du document") String numDocument,
			String codeUniqueDocument, LocalDate dateDocument,
			@NotEmpty(message = "Renseigner le titre") String titreGlobal, String objetDocument, String statutDocument,
			String typeBeneficiaire, String responsableDocument, @NotEmpty(message = "Renseigner le lot") String lot,
			@NotEmpty(message = "Renseigner le nicad") String nicad,
			@NotEmpty(message = "Renseigner la superficie") String superficie, Beneficiaire beneficiaire,
			Commune commune, TypeDocument typeDocument) {
		super();
		this.numDocument = numDocument;
		this.codeUniqueDocument = codeUniqueDocument;
		this.dateDocument = dateDocument;
		this.titreGlobal = titreGlobal;
		this.objetDocument = objetDocument;
		this.statutDocument = statutDocument;
		this.typeBeneficiaire = typeBeneficiaire;
		this.responsableDocument = responsableDocument;
		this.lot = lot;
		this.nicad = nicad;
		this.superficie = superficie;
		this.beneficiaire = beneficiaire;
		this.commune = commune;
		this.typeDocument = typeDocument;
	}
	public Documents(@NotEmpty(message = "Renseigner le numéro du document") String numDocument,
			String codeUniqueDocument, LocalDate dateDocument,
			@NotEmpty(message = "Renseigner le titre") String titreGlobal, String objetDocument, String statutDocument,
			String typeBeneficiaire, String responsableDocument, @NotEmpty(message = "Renseigner le lot") String lot,
			@NotEmpty(message = "Renseigner le nicad") String nicad,
			@NotEmpty(message = "Renseigner la superficie") String superficie, Beneficiaire beneficiaire,
			Commune commune, TypeDocument typeDocument, Collection<PiecesJointes> piecesJointes) {
		super();
		this.numDocument = numDocument;
		this.codeUniqueDocument = codeUniqueDocument;
		this.dateDocument = dateDocument;
		this.titreGlobal = titreGlobal;
		this.objetDocument = objetDocument;
		this.statutDocument = statutDocument;
		this.typeBeneficiaire = typeBeneficiaire;
		this.responsableDocument = responsableDocument;
		this.lot = lot;
		this.nicad = nicad;
		this.superficie = superficie;
		this.beneficiaire = beneficiaire;
		this.commune = commune;
		this.typeDocument = typeDocument;
		this.piecesJointes = piecesJointes;
	}
	
	
	/*
	 * public Documents(String numDocument, String codeUniqueDocument, LocalDate
	 * dateDocument, String titreGlobal, String objetDocument, String
	 * statutDocument, String typeBeneficiaire, String responsableDocument, String
	 * lot, String nicad, String superficie, Beneficiaire beneficiaire, Commune
	 * commune, TypeDocument typeDocument) { super(); this.numDocument =
	 * numDocument; this.codeUniqueDocument = codeUniqueDocument; this.dateDocument
	 * = dateDocument; this.titreGlobal = titreGlobal; this.objetDocument =
	 * objetDocument; this.statutDocument = statutDocument; this.typeBeneficiaire =
	 * typeBeneficiaire; this.responsableDocument = responsableDocument; this.lot =
	 * lot; this.nicad = nicad; this.superficie = superficie; this.beneficiaire =
	 * beneficiaire; this.commune = commune; this.typeDocument = typeDocument; }
	 * public Documents(String numDocument, LocalDate dateDocument, String
	 * titreGlobal, String objetDocument, String statutDocument, String
	 * typeBeneficiaire, String responsableDocument, String lot, String nicad, Date
	 * dateApprobation, String superficie, Beneficiaire beneficiaire, Commune
	 * commune, TypeDocument typeDocument, Collection<PiecesJointes> piecesJointes)
	 * { super(); this.numDocument = numDocument; this.dateDocument = dateDocument;
	 * this.titreGlobal = titreGlobal; this.objetDocument = objetDocument;
	 * this.statutDocument = statutDocument; this.typeBeneficiaire =
	 * typeBeneficiaire; this.responsableDocument = responsableDocument; this.lot =
	 * lot; this.nicad = nicad; this.dateApprobation = dateApprobation;
	 * this.superficie = superficie; this.beneficiaire = beneficiaire; this.commune
	 * = commune; this.typeDocument = typeDocument; this.piecesJointes =
	 * piecesJointes; }
	 */
	

}
