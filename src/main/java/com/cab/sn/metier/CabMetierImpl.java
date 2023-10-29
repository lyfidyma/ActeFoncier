package com.cab.sn.metier;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.cab.sn.controller.FileUploadController;
import com.cab.sn.dao.CommuneArrondissementRepository;
import com.cab.sn.dao.CommuneRepository;
import com.cab.sn.dao.DepartementRepository;
import com.cab.sn.dao.DocumentsRepository;
import com.cab.sn.dao.EntrepriseRepository;
import com.cab.sn.dao.PersonneRepository;
import com.cab.sn.dao.PiecesJointesRepository;
import com.cab.sn.dao.ProfilRepository;
import com.cab.sn.dao.RegionRepository;
import com.cab.sn.dao.ResponsableRepository;
import com.cab.sn.dao.TypeDocumentRepository;
import com.cab.sn.dao.UtilisateurRepository;
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

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CabMetierImpl implements ICabMetier{
	
	@Autowired
	private DocumentsRepository docRepository;
	@Autowired
	private PiecesJointesRepository pjRepository;
	@Autowired
	private PersonneRepository persRepository;
	@Autowired
	private EntrepriseRepository entRepository;
	@Autowired
	private TypeDocumentRepository tdRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private CommuneArrondissementRepository communeArrondRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private StorageService storageService;
	@Autowired
	private ResponsableRepository responsableReposistory;
	@Autowired
	public UtilisateurRepository utilisateurRepository;
	@Autowired
	public ProfilRepository profilRepository;
	
	/**
	 *
	 */
	@Override
	public Documents sauvegarderDocuments(Long idDocument, String numDocument, LocalDate dateDocument, String titreGlobal,
			String objetDocument, String statutDocument, String typeBeneficiaire, String responsableDocument, String lot,
			String nicad, Date dateAPprobation, String superficie, String nomEntreprise, String ninea, String cni, 
			String nomPersonne, String prenom, String nin, LocalDate dateDelivrance, 
			String libelleCommune, String typeDoc, String numPj, LocalDate datePj, String objetPj, MultipartFile file, String numPj1, 
			LocalDate datePj1, String objetPj1, MultipartFile file1) {
		
		Documents d1 = null;
		Entreprise e1 = null ;
		TypeDocument td1 = null;
		Personne p1 = null;
		Commune c1=null;
		//Mise à jour
		if(idDocument!=null){
			Documents doc = docRepository.findById(idDocument).get(); 
			
			//Mise à jour Bénéficiaire
			if(typeBeneficiaire.equals("Entreprise")==true) {
				  if(entRepository.chercherEntrepriseParNinea(ninea)!=null) {
					  if(persRepository.chercherPersonne(cni)!=null) {
						  p1 = persRepository.chercherPersonne(cni);  
					  }else {
						  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
					  }
					  e1 = entRepository.chercherEntrepriseParNinea(ninea); 
					  doc.setEntreprise(e1);
					  doc.setPersonne(p1);
				  }else {
					  if(persRepository.chercherPersonne(cni)!=null) {
						  p1 = persRepository.chercherPersonne(cni);
						   	  
					  }else {
						  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
					  	}
					  e1 = entRepository.save(new Entreprise(nomEntreprise, ninea));
					  }
				  doc.setEntreprise(e1);
				  doc.setPersonne(p1);
			  }
			  else if((typeBeneficiaire.equals("Particulier"))== true) {		 
				  if(persRepository.chercherPersonne(cni)!=null) {
					  		p1 = persRepository.chercherPersonne(cni);
				  }else {
					  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
				  }
					  	doc.setPersonne(p1);
			  }
			  
			  
			  //Mise à jour du type de document
			 // Long idTypeDocMaj = doc.getTypeDocument().getIdTypeDocument();
			  TypeDocument td2 = tdRepository.chercherTypeDocumentParTypeDoc(typeDoc); 
				  doc.setTypeDocument(td2);
				  docRepository.save(doc);
				 
			  //Mise à jour des pièces jointes
				  
			    
			    if(pjRepository.chercherPiecesJointes(idDocument)!=null) {
			    	Collection<PiecesJointes> pj1 = pjRepository.chercherPiecesJointes(idDocument);
			    	
					
						if(pj1.size()==1) {
							PiecesJointes pj2 = pjRepository.chercherPiecesJointes(idDocument).get(0);
							pj2.setDatePj(datePj);
							pj2.setNumPj(numPj);
							pj2.setObjetPj(objetPj);
							if(file.isEmpty()==false) {
								storageService.init();
								storageService.store(file);
								pj2.setCheminFichier(file.getOriginalFilename().toString());
							}
							pjRepository.save(pj2);
						}		 	
						
					
						if(pj1.size()==2) {
							PiecesJointes pj2 = pjRepository.chercherPiecesJointes(idDocument).get(0);
							pj2.setDatePj(datePj);
							pj2.setNumPj(numPj);
							pj2.setObjetPj(objetPj);
							if(file.isEmpty()==false) {
								storageService.init();
								storageService.store(file);
								pj2.setCheminFichier(file.getOriginalFilename().toString());
							}
							pjRepository.save(pj2);	
								
				 			PiecesJointes pj3 = pjRepository.chercherPiecesJointes(idDocument).get(1);
				 			pj3.setDatePj(datePj1);
						 	pj3.setNumPj(numPj1);
						 	pj3.setObjetPj(objetPj1);
						 	if(file1.isEmpty()==false) {
						 		storageService.init();
								storageService.store(file1);
								pj3.setCheminFichier(file1.getOriginalFilename().toString());
						 	}
						 		
						 	pjRepository.save(pj3);		 
					 	}
					 	}else if(pjRepository.chercherPiecesJointes(idDocument)==null) {
					 		  String cheminFichier = null;
							  String cheminFichier1 = null;
							  Collection<PiecesJointes> pj4= new ArrayList<PiecesJointes>();
							  if(numPj.isBlank()== false || objetPj.isBlank()== false || datePj!=null) {
								  if(file.isEmpty()==false) {
								  	storageService.init();
									storageService.store(file);
									Path path = storageService.load(file.getOriginalFilename());
									cheminFichier = MvcUriComponentsBuilder
									          .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString();  
									  
								  }
								  pj4.add(new PiecesJointes(numPj, objetPj, datePj, cheminFichier, d1));
									
							  	  pjRepository.saveAll(pj4);
							  }
							  if(numPj1.isBlank()== false || objetPj1.isBlank() == false || datePj1!=null) {
								  if(file1.isEmpty()==false) {
									  storageService.init();
									  storageService.store(file1);
									  Path path = storageService.load(file1.getOriginalFilename());
									  cheminFichier1 = MvcUriComponentsBuilder
										          .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString();  
								  }
									
								  pj4.add(new PiecesJointes(numPj1, objetPj1, datePj1, cheminFichier1, d1));
								  pjRepository.saveAll(pj4);
							  }
					 	}
			    
		
		
			
			  //Mise à jour de la localisation
			 // Localisation localisation = new Localisation(commune, communeArrond, departement, region);
			  Commune commu= communeRepository.findByCommune(libelleCommune);
			  doc.setCommune(commu);
			  //doc.setLocalisation(localisation);
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
			  doc.setSuperficie(superficie);
			  docRepository.save(doc);
			 
			return doc;			
		}
		
		//Nouvel enregistrement d'un document, l'idDocument est null
		else {
				
			  if(typeBeneficiaire.equals("Entreprise")== true) {
				  if(entRepository.chercherEntrepriseParNinea(ninea)!=null) {
					  if(persRepository.chercherPersonne(cni)!=null) {
						  p1 = persRepository.chercherPersonne(cni);  
					  }else {
						  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
					  }
					  e1 = entRepository.chercherEntrepriseParNinea(ninea); 
				  }else {
					  if(persRepository.chercherPersonne(cni)!=null) {
						  p1 = persRepository.chercherPersonne(cni);
						   	  
					  }else {
						  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
					  	}
					  e1 = entRepository.save(new Entreprise(nomEntreprise, ninea));
					  }
				  
			  }
			  else if((typeBeneficiaire.equals("Particulier"))== true) {		 
				  if(persRepository.chercherPersonne(cni)!=null) {
					  		p1 = persRepository.chercherPersonne(cni);
				  }else {
					  p1 = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));
				  }
					  	
			  }
			  
			  
			  c1 = communeRepository.findByCommune(libelleCommune);
			  td1 = tdRepository.chercherTypeDocumentParTypeDoc(typeDoc); 
			  d1 = docRepository.save(new Documents(numDocument, CodeDocumentGenerateur.genererCodeDocument(new Date(), typeDoc, libelleCommune), 
					  dateDocument, titreGlobal, objetDocument, "saisi", typeBeneficiaire, responsableDocument, lot,nicad,
					  superficie, p1, e1, c1, td1)); 
			 
			  String cheminFichier = null;
			  String cheminFichier1 = null;
			  Collection<PiecesJointes> pj3= new ArrayList<PiecesJointes>();
			  if(numPj.isBlank()== false || objetPj.isBlank()== false || datePj!=null) {
				  if(file.isEmpty()==false) {
				  	storageService.init();
					storageService.store(file);
					Path path = storageService.load(file.getOriginalFilename());
					cheminFichier = MvcUriComponentsBuilder
					          .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString();  
				  }
				  pj3.add(new PiecesJointes(numPj, objetPj, datePj, cheminFichier, d1));
					
			  	  pjRepository.saveAll(pj3);
			  }
			  if(numPj1.isBlank()== false || objetPj1.isBlank() == false || datePj1!=null) {
				  if(file1.isEmpty()==false) {
					  storageService.init();
					  storageService.store(file1);
					  Path path = storageService.load(file1.getOriginalFilename());
					  cheminFichier1 = MvcUriComponentsBuilder
					          .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString();
				  }
					
				  pj3.add(new PiecesJointes(numPj1, objetPj1, datePj1, cheminFichier1, d1));
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
	public Personne chercherPersonne(String cniPersonne) {
		Personne personne=null;
		Optional <Personne> optional = Optional.ofNullable(persRepository.chercherPersonne(cniPersonne));
		if(optional.isPresent())
			return persRepository.chercherPersonne(cniPersonne);
		else if(optional.isEmpty())
			return personne;
		return personne;
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
	public Documents validerDocuments(Long id, String casApprouve, String casRejet, String nomApprobateur, 
			String prenomApprobateur, String motifRejet, MultipartFile file) {
		Documents doc = docRepository.findById(id).get();
		String cheminFichier= null;
		if(casApprouve.equals("true")) {	
			doc.setStatutDocument("approuvé");
			doc.setNomApprobateur(nomApprobateur);
			doc.setPrenomApprobateur(prenomApprobateur);
			doc.setDateApprobation(new Date());
			if(file.isEmpty()==false) {
				storageService.init();
				storageService.store(file);
				Path path = storageService.load(file.getOriginalFilename());
				cheminFichier = MvcUriComponentsBuilder
				          .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString();  
				pjRepository.save(new PiecesJointes(cheminFichier, doc));
				}
			
			docRepository.save(doc);
			
		}else if (casRejet.equals("true")) {
			doc.setStatutDocument("rejeté");
			doc.setDateRejet(new Date());
			doc.setMotifRejet(motifRejet);
			docRepository.save(doc);			
		}
		return doc;
		
	}
	
	@Override
	public List <Documents> findByCni(String cni) {
		return docRepository.findByCni(cni);
	}

	@Override
	public Page<Documents> listDocumentsAValider(int page, int size) {
		return docRepository.findByStatutTransmis(PageRequest.of(page, size, Sort.by("dateCreation").descending()));	
	}

	@Override
	public Page<Documents> chercherDocumentAValider(String motCle, int page, int size) {
		return docRepository.chercherDocumentAValider(motCle, PageRequest.of(page, size));
	}

	@Override
	public Personne ajoutPersonne(Long idPersonne, String nomPersonne, String prenom, String cni,
			String nin, LocalDate dateDelivrance) {
		Personne personne = null;
		if(idPersonne== null) {
			personne = persRepository.save(new Personne(cni, nomPersonne, prenom, nin, dateDelivrance));	  		
		
		}
		else if(idPersonne!=null) {
			personne = persRepository.chercherPersonneParId(idPersonne);
			personne.setCni(cni);
			personne.setDateDelivrance(dateDelivrance);
			personne.setNin(nin);
			personne.setNomPersonne(nomPersonne);
			personne.setPrenom(prenom);
			persRepository.save(personne);
			
		}
		return personne;
	}
	@Override
	public Entreprise ajoutEntreprise(Long idEntreprise, String nomEntreprise, String ninea) {

		Entreprise entreprise = null;
		if(idEntreprise==null) {
			entreprise = entRepository.save(new Entreprise(nomEntreprise, ninea));
		}else if(idEntreprise!=null) {
			entreprise = entRepository.chercherEntreprise(idEntreprise);
			entreprise.setNinea(ninea);
			entreprise.setNomEntreprise(nomEntreprise);
			entRepository.save(entreprise);
			
		}
		return entreprise;
	}

	@Override
	public Entreprise chercherEntrepriseParNinea(String ninea) {
		return entRepository.chercherEntrepriseParNinea(ninea);
	}

	@Override
	public TypeDocument chercherTypeDocumentParTypeDocument(String typeDocument) {
		return tdRepository.chercherTypeDocumentParTypeDoc(typeDocument);
	}

	@Override
	public TypeDocument ajoutTypeDocument(Long idTypeDocument, String typeDoc) {
		TypeDocument typeDocument= null;
		if(idTypeDocument==null) {
			 typeDocument = tdRepository.save(new TypeDocument(typeDoc));
		}else if(idTypeDocument!=null) {
			 typeDocument = tdRepository.findById(idTypeDocument).get();
			 typeDocument.setTypeDoc(typeDoc);
			 tdRepository.save(typeDocument);
		}
		return typeDocument;
	}

	@Override
	public List<TypeDocument> listTypeDocument() {
		return tdRepository.findAll();
	}

	@Override
	public Commune findByCommune(String commune) {
		
		return communeRepository.findByCommune(commune);
	}

	@Override
	public CommuneArrondissement findByCommuneArrondissement(String communeArrond) {
		return communeArrondRepository.findByCommuneArrondissement(communeArrond);
	}

	@Override
	public Departement findByDepartement(String departement) {
		return departementRepository.findByDepartement(departement);
	}

	@Override
	public Region findByRegion(String region) {
		return regionRepository.findByRegion(region);
	}

	@Override
	public Commune ajoutCommune(String commune, String communeArrond, String departement, String region) {
		Commune com=null;
		CommuneArrondissement commArr = null;
		Departement dep = null;
		Region reg = null;
		if(communeArrondRepository.findByCommuneArrondissement(communeArrond)!=null) {
			commArr= communeArrondRepository.findByCommuneArrondissement(communeArrond);
			//dep = departementRepository.findByDepartement(commArr.getDepartement().getLibelleDepartement());
			//reg = regionRepository.findByRegion(dep.getRegion().getLibelleRegion());
		}else {
			if(departementRepository.findByDepartement(departement)!=null) {
				dep = departementRepository.findByDepartement(departement);
				commArr = communeArrondRepository.save(new CommuneArrondissement(communeArrond, dep));
			}else {
				if(regionRepository.findByRegion(region)!=null) {
					reg = regionRepository.findByRegion(region);
					dep = departementRepository.save(new Departement(departement, reg));
					commArr = communeArrondRepository.save(new CommuneArrondissement(communeArrond, dep));
				}else {
					reg = regionRepository.save(new Region(region));
					dep = departementRepository.save(new Departement(departement, reg));
					commArr = communeArrondRepository.save(new CommuneArrondissement(communeArrond, dep));
				}
			}
		}
		
		com = communeRepository.save(new Commune(commune, commArr));
		return com;
	}

	@Override
	public CommuneArrondissement ajoutCommuneArrondissement(String communeArrond, String departement, String region) {
		CommuneArrondissement commArr = null;
		Departement dep = null;
		Region reg = null;
		if(departementRepository.findByDepartement(departement)!=null) {
			dep = departementRepository.findByDepartement(departement);
			//reg = regionRepository.findByRegion(dep.getRegion().getLibelleRegion());
		}else {
			if(regionRepository.findByRegion(region)!=null) {
				reg = regionRepository.findByRegion(region);
				dep = departementRepository.save(new Departement(departement, reg));
			}else {
				reg = regionRepository.save(new Region(region));
				dep = departementRepository.save(new Departement(departement, reg));
			}
		}
		commArr = communeArrondRepository.save(new CommuneArrondissement(communeArrond, dep));
		return commArr;
		
	}

	@Override
	public Departement ajoutDepartement(String departement, String region) {
		Departement dep = null;
		Region reg= null;
		if(regionRepository.findByRegion(region)!=null) {
			reg = regionRepository.findByRegion(region);
		}
		else {
			reg = regionRepository.save(new Region(region));			
		}
		dep = departementRepository.save(new Departement(departement, reg));
		
		return dep;
	}

	@Override
	public Region ajoutRegion(String region) {
		
		return regionRepository.save(new Region(region));
		
	}
	
	public List <Commune> findAllCommune() {
		return communeRepository.findAll();
	}


	@Override
	public TypeDocument findByIdTypeDocument(Long id) {
		return tdRepository.findById(id).get();
	}

	@Override
	public Commune findByIdCommune(Long id) {
		return communeRepository.findById(id).get();
	}

	@Override
	public CommuneArrondissement findByIdCommuneArrondissement(Long id) {
		
		return communeArrondRepository.findById(id).get();
	}

	@Override
	public Departement findByIdDepartement(Long id) {
		return departementRepository.findById(id).get();
	}

	@Override
	public Region findByIdRegion(Long id) {
		
		return regionRepository.findById(id).get();
	}

	@Override
	public List<Personne> findAllPersonne() {
		return persRepository.findAll();
	}

	@Override
	public List<Entreprise> findAllEntreprise() {
		return entRepository.findAll();
	}

	@Override
	public void supprimerPersonne(Long idPersonne) {		
		persRepository.deleteById(idPersonne);
		
	}

	@Override
	public void supprimerEntreprise(Long idEntreprise) {
		entRepository.deleteById(idEntreprise);
		
	}



	

	@Override
	public void supprimerTypeDocument(Long idTypeDocument) {
		tdRepository.deleteById(idTypeDocument);
		
	}

	@Override
	public List<Documents> findByNinea(String ninea) {
		return docRepository.findByNinea(ninea);
	}

	@Override
	public List<Documents> findByTypeDocument(String typeDocument) {
		return docRepository.findByTypeDoc(typeDocument);
	}

	@Override
	public void supprimerCommune(Long idCommune) {
		communeRepository.deleteById(idCommune);	
	}

	@Override
	public List<Documents> findDocumentsByCommune(String libelleCommune) {
		return docRepository.findDocumentsByCommune(libelleCommune);
	}

	@Override
	public Commune modifierCommune(String libelleCommune, String nouvelleCommune) {
		
			Commune comm=null;
			comm = communeRepository.findByCommune(libelleCommune);
			comm.setLibelleCommune(nouvelleCommune);
			communeRepository.save(comm);	
			return comm;
	}

	@Override
	public CommuneArrondissement modifierCommuneArrondissement(String libelleCommuneArrond,
			String nouvelleCommuneArrond) {
		
			CommuneArrondissement cArrond=null;
			cArrond = communeArrondRepository.findByCommuneArrondissement(libelleCommuneArrond);
			cArrond.setLibelleCommuneArrond(nouvelleCommuneArrond);
			communeArrondRepository.save(cArrond);
			return cArrond;
	}

	@Override
	public Departement modifierDepartement(String libelleDepartement, String nouveauDepartement) {
		
		Departement dep = null;
		
			dep = departementRepository.findByDepartement(libelleDepartement);
			dep.setLibelleDepartement(nouveauDepartement);
			departementRepository.save(dep);
			return dep;
	}

	@Override
	public Region modifierRegion(String libelleRegion, String nouvelleRegion) {
		
		Region reg = null;

			reg = regionRepository.findByRegion(libelleRegion);
			reg.setLibelleRegion(nouvelleRegion);
			regionRepository.save(reg);
		return reg;

	}

	@Override
	public List<Responsable> findAllResponsable() {
		return responsableReposistory.findAll();
	}

	@Override
	public Responsable ajoutResponsable(Long id, String nom, String prenom, String fonction) {
		Responsable responsable = null;
		if(id==null) {
			responsableReposistory.save(new Responsable(nom, prenom, fonction));
		}else if(id!=null) {
			responsable = responsableReposistory.findById(id).get();
			responsable.setNomResponsable(nom);
			responsable.setPrenomResponsable(prenom);
			responsable.setFonction(fonction);
			responsableReposistory.save(responsable);
			
		}
		return responsable;
	}

	@Override
	public void supprimerResponsable(Long id) {
		responsableReposistory.deleteById(id);
		
	}

	@Override
	public Responsable findByIdResponsable(Long idResponsable) {
		return responsableReposistory.findById(idResponsable).get();
	}

	@Override
	public List<Utilisateur> findAllUtilisateur() {
		return utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur ajoutUtilisateur(Long id, String nom, String prenom, String email, String password, String profil) {
		Utilisateur utilisateur = null;
		Profil profilRole = profilRepository.findByNomProfil(profil);
		Set<Profil> roles = Stream.of(profilRole)
                .collect(Collectors.toCollection(HashSet::new));
		if(id==null) {
			utilisateur = utilisateurRepository.save(new Utilisateur(nom, prenom, email, password));
			utilisateur.setProfil(roles);
		}
		else if(id!=null) {
			utilisateur = utilisateurRepository.findById(id).get();
			utilisateur.setNomUtilisateur(nom);
			utilisateur.setPrenomUtilisateur(prenom);
			utilisateur.setEmail(email);
			utilisateur.setProfil(roles);
			
			if(password.isBlank()==false)
				utilisateur.setPassword(password);
			utilisateurRepository.save(utilisateur);
		}
		
		return utilisateur;
			
	}

	@Override
	public void supprimerUtilisateur(Long id) {
		utilisateurRepository.deleteById(id);
	}

	@Override
	public Utilisateur findByIdUtilisateur(Long id) {
		return utilisateurRepository.findById(id).get();
	}

	@Override
	public List<Profil> findAllProfil() {
		return profilRepository.findAll();
	}

	@Override
	public Profil ajoutProfil(String nomProfil) {
		return profilRepository.save(new Profil(nomProfil));
	}

	@Override
	public void supprimerProfil(Long id) {
		profilRepository.deleteById(id);
		
	}

	@Override
	public Optional<Profil> findByIdProfil(Long id) {
		return profilRepository.findById(id);
	}

	
	
}
