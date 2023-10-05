package com.cab.sn.metier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	
	/**
	 *
	 */
	@Override
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, LocalDate dateDocument, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, String lot,
			String nicad, Date dateApprobation, String superficie, String nomEntreprise, String ninea, Long cni, 
			String nomPersonne, String prenom, Long nin, LocalDate dateDelivrance, 
			String commune, String communeArrond, String departement, String region,
			String typeDoc, String numPj, LocalDate datePj, String objetPj, String numPj1, LocalDate datePj1, String objetPj1) {
		
		Documents d1 = null;
		Beneficiaire b1 = null ;
		TypeDocument td1 = null;
		Personne p1 = null;
			
		//Mise à jour
		if(idDocument!=null){
			
			Documents doc = docRepository.findById(idDocument).get(); 
			//  Long idBenefMaj=doc.getBeneficiaire().getIdBeneficiaire(); 
			  
				/*
				 * if(typeBeneficiaire.equals("Entreprise")) {
				 * 
				 * if(entRepository.chercherEntreprise(idBenefMaj).getNinea().equals(ninea)) {
				 * b1 = bRepository.findById(idBenefMaj).get();
				 * if(entRepository.chercherEntreprise(idBenefMaj).getPersonne().getCni().equals
				 * (cni)) { b1 = bRepository.findById(idBenefMaj).get(); }else {
				 * if(persRepository.chercherPersonneParId(idBenefMaj).getCni().equals(cni)) {
				 * p1 = persRepository.chercherPersonneParId(idBenefMaj); b1 =
				 * bRepository.save(new Entreprise(nomEntreprise, ninea, p1));
				 * 
				 * }else { p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin,
				 * dateDelivrance)); b1 = bRepository.save(new Entreprise(nomEntreprise, ninea,
				 * p1)); }
				 * 
				 * }
				 * 
				 * }else {
				 * if(entRepository.chercherEntreprise(idBenefMaj).getPersonne().getCni().equals
				 * (cni)) p1 = persRepository.chercherPersonneParId(idBenefMaj); else p1 =
				 * persRepository.save(new Personne(cni, nomPersonne, prenom, nin,
				 * dateDelivrance));
				 * 
				 * b1 = bRepository.save(new Entreprise(nomEntreprise, ninea, p1)); }
				 * 
				 * 
				 * }
				 */
		
				/*
				 * else if(typeBeneficiaire.equals("Particulier")) { b1 = bRepository.save(new
				 * Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
				 * doc.setBeneficiaire(b1); docRepository.save(doc); }
				 */
			  
			  //Mise à jour du type de document
			 // Long idTypeDocMaj = doc.getTypeDocument().getIdTypeDocument();
			  TypeDocument td2 = tdRepository.save(new TypeDocument(typeDoc)); 
				  doc.setTypeDocument(td2);
				  docRepository.save(doc);
				 
			  //Mise à jour des pièces jointes
			    Collection<PiecesJointes> pj1 = pjRepository.chercherPiecesJointes(idDocument);
			//	for(int i=1; i<=pj1.size(); i++) {
					if(numPj!=null || objetPj!=null || datePj!=null) {
						if(pj1.size()==1) {
							PiecesJointes pj2 = pjRepository.chercherPiecesJointes(idDocument).get(0);
							pj2.setDatePj(datePj);
							pj2.setNumPj(numPj);
							pj2.setObjetPj(objetPj);
							pjRepository.save(pj2);		 	
						}
					}
					 	if(numPj1!=null || objetPj1!=null || datePj1!=null) {
					 		if(pj1.size()==1) {
					 			PiecesJointes pj3 = new PiecesJointes(numPj1, objetPj1, datePj1, doc);
					 			pjRepository.save(pj3);
					 		} else if(pj1.size()==2) {
					 			PiecesJointes pj2 = pjRepository.chercherPiecesJointes(idDocument).get(0);
					 			pj2.setDatePj(datePj);
							 	pj2.setNumPj(numPj);
							 	pj2.setObjetPj(objetPj);
							 	pjRepository.save(pj2);
							 	
							 	PiecesJointes pj3 = pjRepository.chercherPiecesJointes(idDocument).get(1);
					 			pj3.setDatePj(datePj1);
							 	pj3.setNumPj(numPj1);
							 	pj3.setObjetPj(objetPj1);
							 	pjRepository.save(pj3);			 
					 		}
					 	}	 	
		//		}
		
			
			  //Mise à jour de la localisation
			  Localisation localisation = new Localisation(commune, communeArrond, departement, region);
			  doc.setLocalisation(localisation);
			  docRepository.save(doc);
			  
			  //Mise à jour du document
			  doc.setNumDocument(numDocument); 
			  doc.setDateDocument(dateDocument); 
			  doc.setTitreGlobal(titreGlobal);
			  doc.setObjetDocument(objetDocument);
			  doc.setTypeBeneficiaire(typeBeneficiaire);
			  doc.setResponsableDocument(responsableDocument);
			  doc.setLot(lot);
			  doc.setNicad(nicad);
			  doc.setDateApprobation(dateApprobation);
			  doc.setSuperficie(superficie);
			  docRepository.save(doc);
			 
			return doc;			
		}
		
		//Nouvel enregistrement d'un document, l'idDocument est null
		else {
				
			  if(typeBeneficiaire.equals("Entreprise")== true) {
				  
				  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, datePj));
				  b1 = bRepository.save(new Entreprise(nomEntreprise, ninea, p1)); 
			  }
			  else if((typeBeneficiaire.equals("Particulier"))== true) {		 
				 // if(cni != (persRepository.chercherPersonne(cni).getCni()))
					  	b1 = bRepository.save(new Personne(cni, nomPersonne, prenom, nin, datePj));
					//b1 = persRepository.chercherPersonne(cni);
			  }
			  
			  Localisation l1 = lRepository.save(new Localisation(commune, communeArrond, departement, region)); 
			  td1 = tdRepository.save(new TypeDocument(typeDoc)); 
			  d1 = docRepository.save(new Documents(numDocument, CodeDocumentGenerateur.genererCodeDocument(new Date(), typeDoc, commune), dateDocument, titreGlobal,
					  objetDocument, "saisi", typeBeneficiaire, responsableDocument, lot,nicad, dateApprobation, 
					  superficie, b1, l1, td1)); 
			  Collection<PiecesJointes> pj3= new ArrayList<PiecesJointes>();
			  if(numPj!=""|| objetPj!="" || datePj!=null) {
				  pj3.add(new PiecesJointes(numPj, objetPj, datePj, d1));
			  	  pjRepository.saveAll(pj3);
			  }
			  if(numPj1!="" || objetPj1!="" || datePj1!=null) {
				  pj3.add(new PiecesJointes(numPj1, objetPj1, datePj1, d1));
				  pjRepository.saveAll(pj3);
			  }
			  return d1;
		}
	}
	
	@Override
	public Page<Documents> listDocuments(int page, int size) { 
			 	return docRepository.findAll(PageRequest.of(page, size, Sort.by("dateCreation").descending()));			
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
	
	@Override
	public List<PiecesJointes> chercherPiecesJointes(Long idDoc) {
		return pjRepository.chercherPiecesJointes(idDoc);
	}
	
	@Override
	public Personne chercherPersonne(Long cniPersonne) {
		return persRepository.chercherPersonne(cniPersonne);
	}

	@Override
	public Personne chercherPersonneParId(Long id) {
		return persRepository.findById(id).get();
		
	}

	@Override
	public Entreprise chercherEntreprise(Long id) {
		return entRepository.findById(id).get();
	}

	@Override
	public List<Documents> tousLesDocuments() {
		return docRepository.findAll();
	}

	@Override
	public int totalBailTransmis() {	
		return docRepository.findByTypeDocBailTransmis().size();
	}

	@Override
	public int totalArreteTransmis() {
		return docRepository.findByTypeDocArreteTransmis().size();
	}

	@Override
	public int totalDecisionTransmis() {
		return docRepository.findByTypeDocDécisionTransmis().size();
	}

	@Override
	public int totalDecretTransmis() {
		return docRepository.findByTypeDocDecretTransmis().size();
	}

	@Override
	public int totalAutresTransmis() {
		return docRepository.findByTypeDocAutresTransmis().size();
	}

	@Override
	public int totalTypeDoc(String typeDoc) {
		return docRepository.findByTypeDoc(typeDoc).size();
	}

	
	@Override
	public int totalBailNonTransmis() {
		return docRepository.findByTypeDocBailNonTransmis().size();
	}

	@Override
	public int totalArreteNonTransmis() {
		return docRepository.findByTypeDocArreteNonTransmis().size();
	}

	@Override
	public int totalDecisionNonTransmis() {
		return docRepository.findByTypeDocDécisionNonTransmis().size();
	}

	@Override
	public int totalDecretNonTransmis() {
		return docRepository.findByTypeDocDecretNonTransmis().size();
	}

	@Override
	public int totalAutresNonTransmis() {
		return docRepository.findByTypeDocAutresNonTransmis().size();
	}

	@Override
	public HashMap<String, Object> getTotalTypeDocDashboard() {
		HashMap<String, Object> typeDocMap = new HashMap<>();
		
		List<Documents> listDocuments = docRepository.findAll();
		List<String> typeDocument = new ArrayList<>();
		List<Long> percentValue = new ArrayList<>();
		
		for(Documents doc:listDocuments) {
			typeDocument.add(doc.getTypeDocument().getTypeDoc());
		}
		
		
		Map <String, Long > map = typeDocument.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		Long totalPercent = map.values().stream().mapToLong(Long::longValue).sum();
		float per=0;
		for(Long value:map.values()) {
			
			
			percentValue.add((value/totalPercent)*100);
			per=(value/totalPercent)*100;
			
		}
		
		
		typeDocMap.put("kTypeDocument", map.keySet());
		typeDocMap.put("vTotalTypeDoc", map.values());
		typeDocMap.put("kPercent", percentValue);

		
		return typeDocMap;
	}
	
	@Override
	public Documents afficherDonneesValidation(String codeUniqueDocument) {	
		return docRepository.findByCodeUniqueDocument(codeUniqueDocument);
	}
	
	@Override
	public Documents validerDocuments(Long id, boolean casApprouve, boolean casRejet) {
		Documents doc = docRepository.findById(id).get();
		if(casApprouve) {	
			doc.setStatutDocument("approuvé");
			doc.setDateApprobation(new Date());
			docRepository.save(doc);
			
		}else if (casRejet) {
			doc.setStatutDocument("rejeté");
			docRepository.save(doc);
			
		}
		return doc;
		
	}
	
	@Override
	public List <Documents> findByCni(Long cni) {
		return docRepository.findByCni(cni);
	}
	
}
