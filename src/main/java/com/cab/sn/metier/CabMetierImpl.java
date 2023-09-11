package com.cab.sn.metier;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.cab.sn.dao.BeneficiaireRepository;
import com.cab.sn.dao.DocumentsRepository;
import com.cab.sn.dao.EntrepriseRepository;
import com.cab.sn.dao.LocalisationRepository;
import com.cab.sn.dao.PersonneRepository;
import com.cab.sn.dao.PiecesJointesRepository;
import com.cab.sn.dao.TypeDocumentRepository;
import com.cab.sn.entities.Beneficiaire;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Localisation;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.entities.TypeDocument;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CabMetierImpl implements ICabMetier{
	
	@Autowired
	private DocumentsRepository docRepository;
	@Autowired
	private BeneficiaireRepository bRepository;
	@Autowired
	private PiecesJointesRepository pjRepository;
	@Autowired
	private LocalisationRepository lRepository;
	@Autowired
	private PersonneRepository persRepository;
	@Autowired
	private EntrepriseRepository entRepository;
	@Autowired
	private TypeDocumentRepository tdRepository;

	@Override
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, Date dateDocument, 
			Date dateCreation, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, int lot,
			String nicad, Date dateApprobation, int superficie, String nomEntreprise, String ninea, Long cni, 
			String nomPersonne, String prenom, Long nin, Date dateDelivrance, 
			String commune, String communeArrond, String departement, String region,
			String typeDoc, String numPj, Date datePj, String objetPj) {
		
		Documents d1 = null;
		Beneficiaire b1 = null;
		//Mise à jour d'un document
		if(idDocument!=null){
			
			  Documents doc = docRepository.findById(idDocument).get(); 
			  Long idBenefMaj=doc.getBeneficiaire().getIdBeneficiaire(); 
			  Beneficiaire beneficiaire =  bRepository.findById(idBenefMaj).get(); 
			//  Personne personne = persRepository.findById(idBenefMaj).get();
			//  Entreprise entreprise = entRepository.findById(idBenefMaj).get();
			  TypeDocument typeDocument= tdRepository.findById(doc.getTypeDocument().getIdTypeDocument()).get();
			  
			  
			  PiecesJointes piecesJointes = pjRepository.chercherPiecesJointes(idDocument);
			  Long idLocalisation = doc.getLocalisation().getIdLocalisation();
			  Localisation localisation = lRepository.findById(idLocalisation).get();
			  
			  doc.setNumDocument(numDocument); 
			  doc.setDateDocument(dateDocument); 
			  doc.setDateCreation(dateCreation);
			  doc.setTitreGlobal(titreGlobal);
			  doc.setObjetDocument(objetDocument);
			  doc.setTypeBeneficiaire(typeBeneficiaire);
			  doc.setResponsableDocument(responsableDocument);
			  doc.setLot(lot);
			  doc.setNicad(nicad);
			  doc.setDateApprobation(dateApprobation);
			  doc.setSuperficie(superficie);
			  doc.setBeneficiaire(beneficiaire);
			  doc.setLocalisation(localisation);
			  doc.setTypeDocument(typeDocument);
			  piecesJointes.setDatePj(datePj); piecesJointes.setNumPj(numPj);
			  piecesJointes.setObjetPj(objetPj); piecesJointes.setDocuments(doc);
			  pjRepository.save(piecesJointes);
			  docRepository.save(doc);
			 
			return null;
			
		}
		//Nouvel enregistrement d'un document
		else {
			
			  if(typeBeneficiaire.equals("Entreprise")== true) {
				  b1 = bRepository.save((new Entreprise(nomEntreprise, ninea))); 
				  Personne p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
			  }
			  else if((typeBeneficiaire.equals("Particulier"))== true) {
				 // Long idPers=persRepository.chercherIdPersonne(cni);
				  //if(cni != (persRepository.findById(idPers).get().getCni()))
					  b1 = bRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
				  //else
					//  b1 = bRepository.findById(persRepository.chercherIdPersonne(cni)).get();
			  }
			  
			  Localisation l1 = lRepository.save(new Localisation(commune, communeArrond, departement, region)); 
			  TypeDocument td1 = tdRepository.save(new TypeDocument(typeDoc)); 
			  d1 = docRepository.save(new Documents(numDocument, dateDocument, dateCreation, titreGlobal,
					  objetDocument, "non transmis", typeBeneficiaire, responsableDocument, lot,nicad, dateApprobation, 
					  superficie, b1, l1, td1)); 
			  PiecesJointes pj1 = pjRepository.save(new PiecesJointes(numPj, objetPj, datePj, d1)); 
			  
			  return d1;
				
				/*
				 * Localisation l1 = lRepository.save(new Localisation(commune, communeArrond,
				 * departement, region)); TypeDocument tp1 = tdRepository.save(new
				 * TypeDocument(typeDoc)); d1 = docRepository.save(new Documents(numDocument,
				 * dateDocument, dateCreation, titreGlobal, objetDocument, "non transmis",
				 * typeBeneficiaire, responsable, lot, nicad, dateApprobation, superficie, b1,
				 * l1, tp1)); PiecesJointes pj1 = pjRepository.save(new PiecesJointes(numPj,
				 * objetPj, datePj, d1));
				 */
				
		}
	}
	
	@Override
	public Page<Documents> listDocuments(int page, int size) { 
			 	return docRepository.findAll(PageRequest.of(page, size));		
		
	}	
	@Override
	public Page<Documents> chercherDocuments(String motCle, int page, int size){
		return docRepository.chercher(motCle, PageRequest.of(page, size));
	}
	
	@Override
	public void supprimerDocuments(Long idDocument) {
		 docRepository.deleteById(idDocument);
	}
	
	@Override
	public Documents modifierDocuments(Long idDocument) {
		return docRepository.findById(idDocument).get();
		
	}
	
	@Override
	public Documents visualiserDocuments(Long idDocument) {
		
		return docRepository.findById(idDocument).get();
	}
	
	@Override
	public void transmettreDocuments(Long idDocument) {
		Documents doc = docRepository.findById(idDocument).get();
		
		doc.setStatutDocument("transmis");
		docRepository.save(doc);
		
	}
	public PiecesJointes chercherPiecesJointes(Long idDoc) {
		return pjRepository.chercherPiecesJointes(idDoc);
	}
}
