package com.cab.sn.metier;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.cab.sn.entities.Commune;
import com.cab.sn.entities.CommuneArrondissement;
import com.cab.sn.entities.Departement;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.entities.Profil;
import com.cab.sn.entities.Region;
import com.cab.sn.entities.Responsable;
import com.cab.sn.entities.TypeDocument;
import com.cab.sn.entities.Utilisateur;


public interface ICabMetier {
	
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, LocalDate dateDocument, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, String lot,
			String nicad, Date dateApprobation, String superficie, String nomEntreprise, String ninea, String cni,
			String nomPersonne, String prenom, String nin, LocalDate dateDelivrance, 
			String libelleCommune, String typeDoc, String numPj, LocalDate datePj, String objetPj, MultipartFile file,
			String numPj1, LocalDate datePj1, String objetPj1, MultipartFile file1);
	public Page<Documents> listDocuments(int page, int size);
	public Page<Documents> chercherDocuments(String motCle, int page, int size);
	public Page<Documents> filtreTypeDocuments(String keySearch, int page, int size);
	public void supprimerDocuments(Long idDocument);
	public Documents modifierDocuments(Long idDocument);
	public Documents visualiserDocuments(Long idDocument);
	public void transmettreDocuments(Long idDocument);
	public List<PiecesJointes> chercherPiecesJointes(Long idDoc);
	public Personne chercherPersonne(String cniPersonne);
	public Personne chercherPersonneParId(Long id);
	public Entreprise chercherEntreprise(Long id);
	public Entreprise chercherEntrepriseParNinea(String ninea);
	public List<Documents> tousLesDocuments();
	public int totalTypeDocument(String typeDoc, String statut);
	public HashMap<String, Object> getTotalTypeDocDashboard();
	public Documents afficherDonneesValidation(String codeUniqueDocument);
	public Documents validerDocuments(Long id, String casApprouve, String casRejet, String nomApprobateur, 
			String prenomApprobateur, String motifRejet, MultipartFile file);
	public List <Documents> findByCni(String cni);
	public Page<Documents> listDocumentsAValider(int page, int size);
	public Page<Documents> chercherDocumentAValider(String motCle, int page, int size);
	public Personne ajoutPersonne(Long idPersonne, String nomPersonne, String prenom, String cni, String nin, 
			LocalDate dateDelivrance);
	public Entreprise ajoutEntreprise(Long idEntreprise, String nomEntreprise, String ninea);
	public TypeDocument chercherTypeDocumentParTypeDocument(String typeDocument);
	public TypeDocument ajoutTypeDocument(Long idTypeDocument, String typeDoc);
	public List<TypeDocument> listTypeDocument();
	public Commune findByCommune(String commune);
	public CommuneArrondissement findByCommuneArrondissement(String communeArrond);
	public Departement findByDepartement(String departement);
	public Region findByRegion(String region);
	public Commune ajoutCommune(String commune, String communeArrond, String departement, String region);
	public CommuneArrondissement ajoutCommuneArrondissement(String communeArrond, String departement, String region);
	public Departement ajoutDepartement(String departement, String region);
	public Region ajoutRegion(String region);
	public List <Commune> findAllCommune();
	public TypeDocument findByIdTypeDocument(Long id);
	public Commune findByIdCommune(Long id);
	public CommuneArrondissement findByIdCommuneArrondissement(Long id);
	public Departement findByIdDepartement(Long id);
	public Region findByIdRegion(Long id);
	public List<Personne> findAllPersonne();
	public List<Entreprise> findAllEntreprise();
	public void supprimerPersonne(Long idPersonne);
	public void supprimerEntreprise(Long idEntreprise);
	public List<Documents> findByNinea(String ninea);
	public void supprimerTypeDocument(Long idTypeDocument);
	public List<Documents> findByTypeDocument(String typeDocument);
	public void supprimerCommune(Long idCommune);
	
	public List<Documents> findDocumentsByCommune(String libelleCommune);
	public Commune modifierCommune (String libelleCommune, String nouvelleCommune);
	public CommuneArrondissement modifierCommuneArrondissement(String libelleCommuneArrond,  String nouvelleCommuneArrond);
	public Departement modifierDepartement(String libelleDepartement, String nouveauDepartement);
	public Region modifierRegion(String libelleRegion, String nouvelleRegion);
	public List<Responsable> findAllResponsable();
	public Responsable ajoutResponsable(Long id, String nom, String prenom, String fonction);
	public void supprimerResponsable(Long id);
	public Responsable findByIdResponsable(Long idResponsable);
	public Optional <Responsable> findByNomResponsable(String nom);
	public Optional <Responsable> findByPrenomResponsable(String prenom);
	public Optional <Responsable> findByFonction(String fonction);
	public List<Utilisateur> findAllUtilisateur();
	public Utilisateur ajoutUtilisateur(Long id, String nom, String prenom, String email, String password, String profil);
	public void supprimerUtilisateur(Long id);
	public Utilisateur findByIdUtilisateur(Long id);
	public Optional <Utilisateur> findByEmail(String email);
	public List<Profil> findAllProfil();
	public Profil ajoutProfil(String nomProfil);
	public void supprimerProfil(Long id);
	public Optional<Profil> findByIdProfil(Long id);
	public Profil findByProfil(String profil);
	public Page<Documents> chercherDocumentParCriteres(String typeDocument, String numDocument, String codeDocument, LocalDate dateDocument, String responsableDocument, int page, int size);
	public Page<Documents> chercherDocumentParBeneficiaire(String numCEDEAO, String cni, String ninea, int page, int size);
	public Page<Documents> chercherDocumentParCommune(String commune, int page, int size);
	public Page<Documents> chercherDocumentParTitreDePropriete(String titreGlobal, String nicad, int page, int size);
	public List<Documents> chercherDocumentParCriteresPourExport(String typeDocument, String numDocument, String codeDocument, LocalDate dateDocument, String responsableDocument);
	public List<Documents> chercherDocumentParBeneficiairePourExport(String numCEDEAO, String cni, String ninea);
	public List<Documents> chercherDocumentParCommunePourExport(String commune);
	public List<Documents> chercherDocumentParTitreDeProprietePourExport(String titreGlobal, String nicad);
	public int totalDocumentBeneficiaire(String cniOuNinea, String typeDoc);
	public int totalDocumentLocalisation(String site, String typeDoc);
	public int totalDocumentCommune(String site, String typeDoc);
	public int totalDocumentCommuneArrond(String site, String typeDoc);
	public int totalDocumentDepartement(String site, String typeDoc);
	public int totalDocumentRegion(String site, String typeDoc);


}
