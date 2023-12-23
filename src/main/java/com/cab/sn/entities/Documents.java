package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
	@Size(max=255, message="Texte trop long")
	private String objetDocument;
	private String statutDocument;
	@NotBlank(message="Choisir un type")
	private String typeBeneficiaire;
	private String responsableDocument;
	@NotEmpty(message="Renseigner le lot")
	private String lot;
	@NotEmpty(message="Renseigner le nicad")
	private String nicad;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private Date dateApprobation;
	@NotEmpty(message="Renseigner la superficie")
	private String superficie;
	private String nomApprobateur;
	private String prenomApprobateur;
	private String motifRejet;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dateRejet;
	
	@ManyToOne
	@JoinColumn(name="idPersonne", updatable=true)
	private Personne personne;
	
	@ManyToOne
	@JoinColumn(name="idEntreprise", updatable = true)
	private Entreprise entreprise;
	
	@ManyToOne
	@JoinColumn(name="idCommune", updatable = true)
	private Commune commune;
	
	@ManyToOne
	@JoinColumn(name="idTypeDocument", updatable = true)
	private TypeDocument typeDocument;
	
	@OneToMany(mappedBy = "documents", cascade = CascadeType.ALL)
	private Collection<PiecesJointes> piecesJointes;
	
	public Documents(@NotEmpty(message = "Renseigner le numéro du document") String numDocument,
			String codeUniqueDocument, LocalDate dateDocument,
			@NotEmpty(message = "Renseigner le titre") String titreGlobal, String objetDocument, String statutDocument,
			String typeBeneficiaire, String responsableDocument, @NotEmpty(message = "Renseigner le lot") String lot,
			@NotEmpty(message = "Renseigner le nicad") String nicad,
			@NotEmpty(message = "Renseigner la superficie") String superficie, Personne personne, Entreprise entreprise,
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
		this.personne = personne;
		this.entreprise = entreprise;
		this.commune = commune;
		this.typeDocument = typeDocument;
	}
	public Documents(@NotEmpty(message = "Renseigner le numéro du document") String numDocument,
			String codeUniqueDocument, LocalDate dateDocument,
			@NotEmpty(message = "Renseigner le titre") String titreGlobal, String objetDocument, String statutDocument,
			String typeBeneficiaire, String responsableDocument, @NotEmpty(message = "Renseigner le lot") String lot,
			@NotEmpty(message = "Renseigner le nicad") String nicad,
			@NotEmpty(message = "Renseigner la superficie") String superficie, Personne personne, Entreprise entreprise,
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
		this.personne = personne;
		this.entreprise = entreprise;
		this.commune = commune;
		this.typeDocument = typeDocument;
		this.piecesJointes = piecesJointes;
	}
	
}
