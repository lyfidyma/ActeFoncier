package com.cab.sn.entities;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
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
	private String numDocument;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dateDocument;
	private Date dateSysteme= new Date();
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dateCreation ;
	private String titreGlobal;
	private String objetDocument;
	private String statutDocument;
	private String typeBeneficiaire;
	private String responsableDocument;
	private int lot;
	private String nicad;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dateApprobation;
	private int superficie;
	@OneToOne
	@JoinColumn(name="idBeneficiaire")
	private Beneficiaire beneficiaire;
	@OneToOne
	@JoinColumn(name="idLocalisation")
	private Localisation localisation;
	@OneToOne
	@JoinColumn(name="idTypeDocument")
	private TypeDocument typeDocument;
	@OneToMany(mappedBy = "documents", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<PiecesJointes> piecesJointes;
	public Documents(String numDocument, Date dateDocument, Date dateCreation, String titreGlobal, String objetDocument,
			String statutDocument, String typeBeneficiaire, String responsableDocument, int lot, String nicad,
			Date dateApprobation, int superficie, Beneficiaire beneficiaire, Localisation localisation,
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

	

}
