package com.cab.sn.metier;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cab.sn.entities.Beneficiaire;
import com.cab.sn.entities.Commune;
import com.cab.sn.entities.CommuneArrondissement;
import com.cab.sn.entities.Departement;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.entities.Region;
import com.cab.sn.entities.TypeDocument;


public interface ICabMetier {
	
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, LocalDate dateDocument, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, String lot,
			String nicad, Date dateApprobation, String superficie, String nomEntreprise, String ninea, Long cni,
			String nomPersonne, String prenom, Long nin, LocalDate dateDelivrance, 
			String comm, String typeDoc, String numPj, LocalDate datePj, String objetPj, 
			String numPj1, LocalDate datePj1, String objetPj1);
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
	public Entreprise chercherEntrepriseParNinea(String ninea);
	public List<Documents> tousLesDocuments();
	public int totalBailTransmis();
	public int totalArreteTransmis();
	public int totalDecisionTransmis();
	public int totalDecretTransmis();
	public int totalAutresTransmis();
	public int totalBailNonTransmis();
	public int totalArreteNonTransmis();
	public int totalDecisionNonTransmis();
	public int totalDecretNonTransmis();
	public int totalAutresNonTransmis();
	public int totalTypeDoc(String typeDoc);
	public HashMap<String, Object> getTotalTypeDocDashboard();
	public Documents afficherDonneesValidation(String codeUniqueDocument);
	public Documents validerDocuments(Long id, String casApprouve, String casRejet);
	public List <Documents> findByCni(Long cni);
	public Page<Documents> listDocumentsAValider(int page, int size);
	public Page<Documents> chercherDocumentAValider(String motCle, int page, int size);
	public Beneficiaire ajoutBeneficiaire(String typeBeneficiaire, String nomPersonne, String prenom, Long cni, Long nin, 
			LocalDate dateDelivrance, String nomEntreprise, String ninea, boolean checkbox);
	public TypeDocument chercherTypeDocumentParTypeDocument(String typeDocument);
	public TypeDocument ajoutTypeDocument(String typeDoc);
	public List<TypeDocument> listTypeDocument();
	public Commune findByCommune(String commune);
	public CommuneArrondissement findByCommuneArrondissement(String communeArrond);
	public Departement findByDepartement(String departement);
	public Region findByRegion(String region);
	public Commune ajoutCommune(String commune, String communeArrond, String departement, String region);
	public CommuneArrondissement ajoutCommuneArrondissement(String communeArrond, String departement, String region);
	public Departement ajoutDepartement(String departement, String region);
	public Region ajoutRegion(String region);
	

}
