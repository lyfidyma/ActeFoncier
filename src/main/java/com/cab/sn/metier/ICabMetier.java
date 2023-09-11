package com.cab.sn.metier;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;

import com.cab.sn.entities.Documents;


public interface ICabMetier {
	
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, Date dateDocument, 
			Date dateCreation, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, int lot,
			String nicad, Date dateApprobation, int superficie, String nomEntreprise, String ninea, Long cni,
			String nomPersonne, String prenom, Long nin, Date dateDelivrance, 
			String commune, String communeArrond, String departement, String region,
			String typeDoc, String numPj, Date datePj, String objetPj);
	public Page<Documents> listDocuments(int page, int size);
	public Page<Documents> chercherDocuments(String motCle, int page, int size);
	public void supprimerDocuments(Long idDocument);
	public Documents modifierDocuments(Long idDocument);
	public Documents visualiserDocuments(Long idDocument);
	public void transmettreDocuments(Long idDocument);

}
