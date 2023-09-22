package com.cab.sn.metier;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;


public interface ICabMetier {
	
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, LocalDate dateDocument, 
			LocalDate dateCreation, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, int lot,
			String nicad, LocalDate dateApprobation, int superficie, String nomEntreprise, String ninea, Long cni,
			String nomPersonne, String prenom, Long nin, LocalDate dateDelivrance, 
			String commune, String communeArrond, String departement, String region,
			String typeDoc, String numPj, LocalDate datePj, String objetPj, String numPj1, LocalDate datePj1, String objetPj1);
	public Page<Documents> listDocuments(int page, int size);
	public Page<Documents> chercherDocuments(String motCle, int page, int size);
	public void supprimerDocuments(Long idDocument);
	public Documents modifierDocuments(Long idDocument);
	public Documents visualiserDocuments(Long idDocument);
	public void transmettreDocuments(Long idDocument);
	public List<PiecesJointes> chercherPiecesJointes(Long idDoc);
	public Personne chercherPersonne(Long cniPersonne);
	public Personne chercherPersonneParId(Long id);
	public Entreprise chercherEntreprise(Long id);

}
