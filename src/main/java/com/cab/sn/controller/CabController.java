package com.cab.sn.controller;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cab.sn.entities.Beneficiaire;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.entities.TypeDocument;
import com.cab.sn.metier.ICabMetier;
import com.cab.sn.metier.StorageService;

import jakarta.validation.Valid;


@Controller
public class CabController {
	
	@Autowired
	private ICabMetier cabMetier;
	@Autowired
	private StorageService storageService;
	

	@RequestMapping(value={"/", "/index"})
	public String index() {		
		return "index";
	}
	
	@RequestMapping("/ajouter")
	public String ajouter(Model model, @ModelAttribute("unDocument") Documents documents, 
			@ModelAttribute("unePersonne") Personne personne, @ModelAttribute("uneEntreprise") Entreprise entreprise) {
		List<TypeDocument> type = cabMetier.listTypeDocument();
			model.addAttribute("unDocument",documents);	
			model.addAttribute("unePersonne",personne);
			model.addAttribute("uneEntreprise",entreprise);
			model.addAttribute("type",type);	
		return "formulaire";
	}
	
	@RequestMapping("/cons")
	public String consulterTousDoc(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size,
			String motCle) {
		Page<Documents> pageDocuments ;
			if(motCle!=null) {
				pageDocuments = cabMetier.chercherDocuments(motCle, page, size);
				model.addAttribute("listDocuments", pageDocuments);
				model.addAttribute("motCle", motCle);
			}
			else {
				pageDocuments = cabMetier.listDocuments(page, size);
				model.addAttribute("listDocuments", pageDocuments);
				model.addAttribute("motCle", motCle);
			}
			
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("totalItems", pageDocuments.getTotalElements());
			model.addAttribute("totalPages", pageDocuments.getTotalPages());
			
			
		return "docum";
	}
	
	
	@RequestMapping(value="/sauvegarderDocument", method=RequestMethod.POST)
	public String sauvegarder(@Valid @ModelAttribute("unDocument") Documents doc, BindingResult result, @Valid @ModelAttribute("unePersonne") Personne personne,
			Errors errors, 
			@ModelAttribute("uneEntreprise") Entreprise entreprise, Model model, Long idDocument, String numDocument, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  dateDocument,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreation, 
			String titreGlobal,String objetDocument, String statutDocument, 
			String typeBeneficiaire,String responsableDocument, String lot, String nicad, Date dateApprobation, String superficie, String nomEntreprise,
			String ninea, Long cni, String nomPersonne, String prenom, Long nin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDelivrance, 
			String comm, String typeDoc, 
			String numPj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj, String objetPj,
			String numPj1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj1, String objetPj1, @RequestParam(name="flag",defaultValue="") String  flag,
			@RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1) {
		
		if (result.hasErrors()) {
			  List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
		
		  if (errors.hasErrors()) {
			  List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
		  
		  
		  String messageDoublon ="";
		  String message = "";
		  String messageCEDEAO = "";
		  String messageTypeDoc = "";
		  String regex = "^[1-2]\\d{12}$";
		  String regexCEDEAO = "^[1-2]\\d{16}$";
		  Pattern pattern = Pattern.compile(regex);
		  Pattern patternCEDEAO = Pattern.compile(regexCEDEAO);
		  	  
		  
		if(cni!=null) {
		 Matcher matcher = pattern.matcher(cni.toString());
		 if (matcher.find()==false) {
			 message = "Renseigner la cni au bon format";
			 model.addAttribute("messageCni", message);
			 List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);	
			 if(idDocument == null)
				  	return "formulaire"; 
			  return "modifier"; 
		 }
	}
		 
		 if(nin!=null) {
			 Matcher matcherCEDEAO = patternCEDEAO.matcher(nin.toString());
			 if (matcherCEDEAO.find()==false) {
				 messageCEDEAO = "Renseigner le numéro CEDEAO au bon format";
				 model.addAttribute("messageCEDEAO", messageCEDEAO);
				 List<TypeDocument> type = cabMetier.listTypeDocument();
					model.addAttribute("type",type);	
				 if(idDocument == null)
					  	return "formulaire"; 
				  return "modifier"; 
			 }
				 
		 }
		 
		 
		 if(typeDoc=="")
		 {
			 messageTypeDoc ="Choisir un type";
			 model.addAttribute("messageTypeDoc", messageTypeDoc);
			 List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);	
			 if(idDocument== null)
				  	return "formulaire"; 
			  return "modifier";
		 }
		 
			  List <Documents> controleDocument = cabMetier.findByCni(cni);
			  
			  if(cabMetier.findByCni(cni)!=null) { for(Documents document:controleDocument)
			  { if(document.getTypeDocument().getTypeDoc().equals(typeDoc) &&
			  document.getNumDocument().equals(numDocument) ){
			  if(document.getTypeBeneficiaire().equals("Particulier")){
			  if(cabMetier.chercherPersonneParId(document.getBeneficiaire().
			  getIdBeneficiaire()).getCni().equals(cni)) {
			  messageDoublon="Ce document existe sous le numéro "+document.
			  getCodeUniqueDocument(); 
			  model.addAttribute("messageDoublon",messageDoublon); 
			  List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);	
			  return "formulaire"; 
			  }
			  
			  }else if (document.getTypeBeneficiaire().equals("Entreprise")) {
			  if(cabMetier.chercherEntreprise(document.getBeneficiaire().getIdBeneficiaire(
			  )).getNinea().equals(ninea)){
			  if(cabMetier.chercherEntreprise(document.getBeneficiaire().getIdBeneficiaire(
			  )).getPersonne().getCni().equals(cni)) {
			  messageDoublon="Ce document existe sous le numéro "+document.
			  getCodeUniqueDocument(); 
			  model.addAttribute("messageDoublon", messageDoublon); 
			  List<TypeDocument> type = cabMetier.listTypeDocument();	
				model.addAttribute("type",type);	
			  return "formulaire"; } } }
			  
			  } } }
		
			 
			doc = cabMetier.sauvegarderDocuments(idDocument, numDocument, dateDocument, titreGlobal, 
					objetDocument, statutDocument, typeBeneficiaire, responsableDocument, lot, nicad, 
					dateApprobation, superficie, nomEntreprise, ninea, cni, nomPersonne, prenom, nin, dateDelivrance, comm, typeDoc, 
					numPj, datePj, objetPj, numPj1, datePj1, objetPj1);
			
			if(file.isEmpty()==false) {
				storageService.init();
				storageService.store(file);
				}
				if(file1.isEmpty()==false) {
					storageService.init();
					storageService.store(file1);
				}
				//redirectAttributes.addFlashAttribute("message",
					//	"Fichier chargé " + file.getOriginalFilename() + "!");
			
			flag="1";
			model.addAttribute("unDocument", doc);
			model.addAttribute("flag", flag);
			
			if(idDocument== null) {
			  	return "formulaire"; 
			}
		  return "redirect:/visualiser?id="+idDocument;
		
	}
	
	@RequestMapping(value="/supprimer", method=RequestMethod.GET)
	public String supprimer(Long id, int page, int size, String motCle) {
		cabMetier.supprimerDocuments(id);
		return "redirect:/cons?page="+page+"&size="+size+"";
	}
	
	@RequestMapping(value="/modifier", method=RequestMethod.GET)
	public String modifier(Model model, Long id  ) {
		
		String dateDelivranceString = "jj/mm/yyyy";
		String dateDocumentString = "jj/mm/yyyy";
		String dateApprobationString="jj/mm/yyyy";
		
		Documents doc = cabMetier.modifierDocuments(id);
		List <PiecesJointes> pj= cabMetier.chercherPiecesJointes(id);
		if(cabMetier.modifierDocuments(id).getDateDocument()!=null)
			 dateDocumentString = cabMetier.modifierDocuments(id).getDateDocument().toString();
		//if(cabMetier.modifierDocuments(id).getDateApprobation()!=null)
			// dateApprobationString =cabMetier.modifierDocuments(id).getDateApprobation().toString();
		Long idbenef = cabMetier.modifierDocuments(id).getBeneficiaire().getIdBeneficiaire();
		if(doc.getTypeBeneficiaire().equals("Particulier")) {
			if(cabMetier.chercherPersonneParId(idbenef).getDateDelivrance()!=null)
				dateDelivranceString = cabMetier.chercherPersonneParId(idbenef).getDateDelivrance().toString();
		}
		else if(doc.getTypeBeneficiaire().equals("Entreprise")) {
			if(cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance()!=null)
				dateDelivranceString = cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance().toString();
		}
		if(cabMetier.chercherPiecesJointes(id)!=null) {
			if(cabMetier.chercherPiecesJointes(id).size()==1) {
				String datePieceJointeString = cabMetier.chercherPiecesJointes(id).get(0).getDatePj().toString();
				model.addAttribute("datePieceJointeString", datePieceJointeString);
			}else if(cabMetier.chercherPiecesJointes(id).size()==2) {
				String datePieceJointeString = cabMetier.chercherPiecesJointes(id).get(0).getDatePj().toString();
				String datePieceJointeString1 = cabMetier.chercherPiecesJointes(id).get(0).getDatePj().toString();
				model.addAttribute("datePieceJointeString", datePieceJointeString);
				model.addAttribute("datePieceJointeString1", datePieceJointeString1);
			}
		}
		model.addAttribute("unDocument",doc);
		model.addAttribute("piecesJointes", pj);
		model.addAttribute("dateDocumentString",dateDocumentString);
		model.addAttribute("dateApprobationString",dateApprobationString);
		model.addAttribute("dateDelivranceString", dateDelivranceString);
		
		return "modifier";
	}
	
	@RequestMapping(value="/visualiser", method=RequestMethod.GET)
	public String visualiser(Model model, Long id) {
		Documents doc = cabMetier.visualiserDocuments(id);
		List <PiecesJointes> pj= cabMetier.chercherPiecesJointes(id);
		model.addAttribute("unDocument", doc);
		model.addAttribute("piecesJointes", pj);
		return "transmission";
	}
	
	@RequestMapping(value="/transmettre", method=RequestMethod.POST)
	public String transmission(Model model, Long idDocument) {
			cabMetier.transmettreDocuments(idDocument);
		return "redirect:/cons";
	}
	
	@RequestMapping(value="/modal", method= RequestMethod.POST)
	public String fenetreModal(Model model, Long idDocument, String numDocument, String typeBeneficiaire, 
			String nomEntreprise,
			String nomPersonne,
			String typeDoc) {
		
		model.addAttribute("typeDoc", typeDoc);
		model.addAttribute("numDocument", numDocument);
		model.addAttribute("typeBeneficiaire", typeBeneficiaire);
		model.addAttribute("nomPersonne", nomPersonne);
		model.addAttribute("nomEntreprise", nomEntreprise);
		return "fenetreModal";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String tableauDeBord(Model model) {
		model.addAttribute("totalBailTransmis", cabMetier.totalBailTransmis());
		model.addAttribute("totalArreteTransmis", cabMetier.totalArreteTransmis());
		model.addAttribute("totalDecisionTransmis", cabMetier.totalDecisionTransmis());
		model.addAttribute("totalDecretTransmis", cabMetier.totalDecretTransmis());
		model.addAttribute("totalAutresTransmis", cabMetier.totalAutresTransmis());
		
		model.addAttribute("totalBailNonTransmis", cabMetier.totalBailNonTransmis());
		model.addAttribute("totalArreteNonTransmis", cabMetier.totalArreteNonTransmis());
		model.addAttribute("totalDecisionNonTransmis", cabMetier.totalDecisionNonTransmis());
		model.addAttribute("totalDecretNonTransmis", cabMetier.totalDecretNonTransmis());
		model.addAttribute("totalAutresNonTransmis", cabMetier.totalAutresNonTransmis());
		
		model.addAttribute("diagTypeDoc", cabMetier.getTotalTypeDocDashboard());
		return "dashboard";
	}
	
	@RequestMapping(value="/afficherDonneesValidation")
	public String validerDocumnents(Model model, @RequestParam(name="codeUniqueDocument",defaultValue="")String codeUniqueDocument,
			@RequestParam(name="messageValiderOuRejeter",defaultValue="")String messageValiderOuRejeter) {
		String dateDelivranceString = "jj/mm/yyyy";
		String dateDocumentString = "jj/mm/yyyy";
		//String dateApprobationString="jj/mm/yyyy";
		
		Documents doc = cabMetier.afficherDonneesValidation(codeUniqueDocument);
		
		  List <PiecesJointes> pj= cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument());
		  if(cabMetier.afficherDonneesValidation(codeUniqueDocument).getDateDocument()!=null) 
			 dateDocumentString = cabMetier.afficherDonneesValidation(codeUniqueDocument).getDateDocument().toString(); 
		  //if(cabMetier.modifierDocuments(id).getDateApprobation()!=null)
		  // dateApprobationString=cabMetier.modifierDocuments(id).getDateApprobation().toString(); 
		  Long idbenef =  cabMetier.afficherDonneesValidation(codeUniqueDocument).getBeneficiaire().getIdBeneficiaire(); 
		  if(doc.getTypeBeneficiaire().equals("Particulier")) {
			  if(cabMetier.chercherPersonneParId(idbenef).getDateDelivrance()!=null)
				  	dateDelivranceString = cabMetier.chercherPersonneParId(idbenef).getDateDelivrance().toString(); 
			  }
		  	  else if(doc.getTypeBeneficiaire().equals("Entreprise")) {
		  		  if(cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance()!= null) 
		  			  dateDelivranceString =  cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance().toString(); 
		  		  } 
		  if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument())!=null) {
			  if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument()).size()==1) { 
				  String datePieceJointeString = cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString();
				  	model.addAttribute("datePieceJointeString", datePieceJointeString); 
				  	}else
				  		if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument()).size()==2) {
				  			String datePieceJointeString = cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString(); 
				  			String datePieceJointeString1 = cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString();
				  			model.addAttribute("datePieceJointeString", datePieceJointeString);
				  			model.addAttribute("datePieceJointeString1", datePieceJointeString1); } }
		 
		model.addAttribute("unDocument",doc);
		
		  model.addAttribute("piecesJointes", pj);
		  model.addAttribute("dateDocumentString",dateDocumentString);
		  //model.addAttribute("dateApprobationString",dateApprobationString);
		  model.addAttribute("dateDelivranceString", dateDelivranceString);
		  model.addAttribute("messageValiderOuRejeter", messageValiderOuRejeter);
		 
		
		return "validation";
	}
	
	@RequestMapping(value= "/pageValidation")
	public String pageValidation(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size,
			String motCle) {
		
			Page<Documents> pageDocuments ;
				if(motCle!=null) {
					pageDocuments = cabMetier.chercherDocumentAValider(motCle, page, size);
					model.addAttribute("listDocuments", pageDocuments);
					model.addAttribute("motCle", motCle);
				}
				else {
					pageDocuments = cabMetier.listDocumentsAValider(page, size);
					model.addAttribute("listDocuments", pageDocuments);
					model.addAttribute("motCle", motCle);
				}
				
				model.addAttribute("currentPage", page);
				model.addAttribute("size", size);
				model.addAttribute("totalItems", pageDocuments.getTotalElements());
				model.addAttribute("totalPages", pageDocuments.getTotalPages());
		return "validation";
	}
	
	@RequestMapping(value="/valider", method=RequestMethod.POST)
	public String valider(Model model, Long idDoc, String checkboxValider, String checkboxRejeter,
			MultipartFile file) {
		/*
		 * String messageValiderOuRejeter=""; if((checkboxValider && checkboxRejeter) ||
		 * (checkboxValider==false && checkboxRejeter==false)) { messageValiderOuRejeter
		 * = "Approuvez ou rejetez le document";
		 * //model.addAttribute("messageValiderOuRejeter", messageValiderOuRejeter);
		 * return
		 * "redirect:/afficherDonneesValidation?codeUniqueDocument="+codeUniqueDocument+
		 * "&messageValiderOuRejeter="+messageValiderOuRejeter+""; }
		 */
			cabMetier.validerDocuments(idDoc, checkboxValider, checkboxRejeter);
			if(checkboxValider.equals("true")) {
			if(file.isEmpty()==false) {
				storageService.init();
				storageService.store(file);
				}
			}
				
		return "redirect:/pageValidation";
	}
	@RequestMapping(value={"/beneficiaire"})
	public String beneficiaire() {		
		return "beneficiaire";
	}
	
	@RequestMapping("/referentielBeneficiaire")
	public String ajoutBeneficiaire(Beneficiaire beneficiaire, Model model, String typeBeneficiaire, String nomPersonne, String prenom, Long cni, Long nin, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateDelivrance, String nomEntreprise, String ninea, 
			boolean checkbox) {
		String messageCni = "";
		String messageNin = "";
		String regex = "^[1-2]\\d{12}$";
		String regexCEDEAO = "^[1-2]\\d{16}$";
		Pattern pattern = Pattern.compile(regex);
		Pattern patternCEDEAO = Pattern.compile(regexCEDEAO);
		
		if(typeBeneficiaire.equals("Particulier")) {
			
		if(nomPersonne.isBlank() || prenom.isBlank() || cni == null || nin == null || dateDelivrance == null) {
			model.addAttribute("messageChampsObligatoires", "Tous les champs sont obligatoires");
			 model.addAttribute("nomPersonne", nomPersonne);
			 model.addAttribute("prenom", prenom);
			 model.addAttribute("cni", cni);
			 model.addAttribute("nin", nin);
			 model.addAttribute("dateDelivrance", dateDelivrance);
			return "beneficiaire";
		}
		
		 
		 if(cni!=null) {
		 Matcher matcher = pattern.matcher(cni.toString());
		 if (matcher.find() == false) {
			 messageCni = "Renseigner la cni au bon format";
			 model.addAttribute("messageCni", messageCni);
			 model.addAttribute("nomPersonne", nomPersonne);
			 model.addAttribute("prenom", prenom);
			 model.addAttribute("cni", cni);
			 model.addAttribute("nin", nin);
			 model.addAttribute("dateDelivrance", dateDelivrance);
			 return "beneficiaire"; 
		 }
	}
		 
		 if(nin!=null) {
			 Matcher matcherCEDEAO = patternCEDEAO.matcher(nin.toString());
			 if (matcherCEDEAO.find() == false) {
				 messageNin = "Renseigner le numéro CEDEAO au bon format";
				 model.addAttribute("messageNin", messageNin);
				 model.addAttribute("messageCni", messageCni);
				 model.addAttribute("nomPersonne", nomPersonne);
				 model.addAttribute("prenom", prenom);
				 model.addAttribute("cni", cni);
				 model.addAttribute("nin", nin);
				 model.addAttribute("dateDelivrance", dateDelivrance);
				 return "beneficiaire"; 
			 }
				 
		 }
		 
		
	if (cabMetier.chercherPersonne(cni)!= null) {
		model.addAttribute("messageDoublon", "Cette personne existe déjà");
		 model.addAttribute("messageCni", messageCni);
		 model.addAttribute("nomPersonne", nomPersonne);
		 model.addAttribute("prenom", prenom);
		 model.addAttribute("cni", cni);
		 model.addAttribute("nin", nin);
		 model.addAttribute("dateDelivrance", dateDelivrance);
		return "beneficiaire";
		
	} 
	beneficiaire = cabMetier.ajoutBeneficiaire(typeBeneficiaire, nomPersonne, prenom, cni, nin, 
			dateDelivrance, nomEntreprise, ninea, checkbox);
	model.addAttribute("messageSucces", "Particulier enregistré");
	return "beneficiaire";
	}
		
		if(typeBeneficiaire.equals("Entreprise")) {
				if(nomEntreprise.isBlank() || ninea.isBlank()) {
				model.addAttribute("messageChampsObligatoires", "Tous les champs sont obligatoires");
				model.addAttribute("nomEntreprise", nomEntreprise);
				model.addAttribute("ninea", ninea);
				model.addAttribute("typeBeneficiaire", typeBeneficiaire);
				return "beneficiaire";
			}
			if(ninea!=null) {
			if(cabMetier.chercherEntrepriseParNinea(ninea)!= null) {
				model.addAttribute("messageDoublon", "Cette entreprise existe déjà");
				model.addAttribute("nomEntreprise", nomEntreprise);
				 model.addAttribute("ninea", ninea);
				 model.addAttribute("typeBeneficiaire", typeBeneficiaire);
				return "beneficiaire";
			}
		}
			if(checkbox) {
			if(nomPersonne.isBlank() || prenom.isBlank() || 
					cni == null || nin == null || dateDelivrance == null) {
				model.addAttribute("messageChampsObligatoires", "Tous les champs sont obligatoires");
				model.addAttribute("nomEntreprise", nomEntreprise);
				 model.addAttribute("ninea", ninea);
				 model.addAttribute("nomPersonne", nomPersonne);
				 model.addAttribute("prenom", prenom);
				 model.addAttribute("cni", cni);
				 model.addAttribute("nin", nin);
				 model.addAttribute("dateDelivrance", dateDelivrance);
				return "beneficiaire";
			}
		}
			beneficiaire = cabMetier.ajoutBeneficiaire(typeBeneficiaire, nomPersonne, prenom, cni, nin,
					dateDelivrance, nomEntreprise, ninea, checkbox);
			model.addAttribute("messageSucces", nomEntreprise+" enregistrée");
			return "beneficiaire";
		}
		return "redirect:/referentielBeneficiaire";
	}
	
	
	@RequestMapping("/typededocument")
	public String typeDeDocument() {
		return "typededocument";
	}
	
	@RequestMapping("/referentielTypeDocument")
	public String ajoutTypeDocument(Model model, TypeDocument typeDocument, String typeDoc) {
		if(typeDoc.isBlank()) {
			model.addAttribute("messageChampsObligatoires", "Donner un type");
			model.addAttribute("typeDoc", typeDoc);
			return "typededocument";
		}
		if(cabMetier.chercherTypeDocumentParTypeDocument(typeDoc)!=null) {
			model.addAttribute("messageDoublon", cabMetier.chercherTypeDocumentParTypeDocument(typeDoc).getTypeDoc()+" existe déjà");
			model.addAttribute("typeDoc", typeDoc);
			return "typededocument";
		}
		
		typeDocument=cabMetier.ajoutTypeDocument(typeDoc);
		model.addAttribute("messageSucces", typeDoc+" enregistré(e)");
		return "typededocument";
	}
	
	@RequestMapping("/localisation")
	public String localisation() {
		return "localisation";
	}
	
	@RequestMapping("/referentielLocalisation")
	public String ajoutLocalisation(Model model, String commune, String communeArrond, String departement, String region ) {
		
		if(commune.isBlank() && communeArrond.isBlank() && departement.isBlank() && region.isBlank()) {
			model.addAttribute("messageChampsObligatoires", "Tous les champs sont obligatoires");
		return "localisation";
		}
		if(commune.isBlank()==false && (communeArrond.isBlank() || departement.isBlank() || region.isBlank())) {
			if(cabMetier.findByCommune(commune)!=null) {
				model.addAttribute("messageDoublon", commune+" existe déjà");
				model.addAttribute("commune", commune);
				model.addAttribute("communeArrond", communeArrond);
				model.addAttribute("departement", departement);
				model.addAttribute("region", region);
			return "localisation";
			}else {
			model.addAttribute("messageChampsObligatoires", "Renseigner la commune d'arrondissement, le département et la région");
			model.addAttribute("commune", commune);
			model.addAttribute("communeArrond", communeArrond);
			model.addAttribute("departement", departement);
			model.addAttribute("region", region);
		return "localisation";
			}
		}
		if(communeArrond.isBlank()==false && (departement.isBlank() || region.isBlank())) {
			if(cabMetier.findByCommuneArrondissement(communeArrond) != null) {
				model.addAttribute("messageDoublon", communeArrond+" existe déjà");
				model.addAttribute("commune", commune);
				model.addAttribute("communeArrond", communeArrond);
				model.addAttribute("departement", departement);
				model.addAttribute("region", region);
			return "localisation";
				
			} else {
			model.addAttribute("messageChampsObligatoires", "Renseigner le département et la région");
			model.addAttribute("commune", commune);
			model.addAttribute("communeArrond", communeArrond);
			model.addAttribute("departement", departement);
			model.addAttribute("region", region);
		return "localisation";
			}
		}
		if(departement.isBlank()== false && region.isBlank()) {
			if(cabMetier.findByDepartement(departement) != null) {
				model.addAttribute("messageDoublon", departement+" existe déjà");
				model.addAttribute("commune", commune);
				model.addAttribute("communeArrond", communeArrond);
				model.addAttribute("departement", departement);
				
			return "localisation";
			}else {
			model.addAttribute("messageChampsObligatoires", "Renseigner la région");
			model.addAttribute("commune", commune);
			model.addAttribute("communeArrond", communeArrond);
			model.addAttribute("departement", departement);
			
		return "localisation";
			}
		} else if(departement.isBlank()== false && region.isBlank()== false && (commune.isBlank() && communeArrond.isBlank())) {
		
			if(cabMetier.findByDepartement(departement) != null) {
				model.addAttribute("messageDoublon", departement+" existe déjà");
				model.addAttribute("departement", departement);
				model.addAttribute("region", region);
				return "localisation";	
			}
			
			cabMetier.ajoutDepartement(departement, region);
			model.addAttribute("messageSucces", departement+" enregistré");
			model.addAttribute("departement", departement);
			model.addAttribute("region", region);
			return "localisation";
		}
		
		if((commune.isBlank() && communeArrond.isBlank() && departement.isBlank() && region.isBlank()==false)) {
			if(cabMetier.findByRegion(region)!= null) {
				model.addAttribute("messageDoublon", region+" existe déjà");
				model.addAttribute("region", region);
			return "localisation";
		}else {
			cabMetier.ajoutRegion(region);
			model.addAttribute("messageSucces", region+" enregistrée");
			return "localisation";
		}
		}
		
		if(commune.isBlank() && communeArrond.isBlank()==false && departement.isBlank()==false && region.isBlank()==false) {
			if(cabMetier.findByCommuneArrondissement(communeArrond)!=null) {
				model.addAttribute("messageDoublon", communeArrond+" existe déjà");
				model.addAttribute("communeArrond", communeArrond);
				model.addAttribute("departement", departement);
				model.addAttribute("region", region);
				return "localisation";	
			}
			cabMetier.ajoutCommuneArrondissement(communeArrond, departement, region);
			model.addAttribute("messageSucces", communeArrond+" enregistrée");
			model.addAttribute("departement", departement);
			model.addAttribute("region", region);
			return "localisation";
		}
		
		if(commune.isBlank()==false && communeArrond.isBlank()==false && departement.isBlank()==false && region.isBlank()==false) {
						
			if(cabMetier.findByCommune(commune)!=null) {
				model.addAttribute("messageDoublon", commune+" existe déjà");
				model.addAttribute("commune", commune);
				model.addAttribute("communeArrond", communeArrond);
				model.addAttribute("departement", departement);
				model.addAttribute("region", region);
				return "localisation";	
			}
			
			cabMetier.ajoutCommune(commune, communeArrond, departement, region);
			model.addAttribute("messageSucces", commune+" enregistrée");
			model.addAttribute("communeArrond", communeArrond);
			model.addAttribute("departement", departement);
			model.addAttribute("region", region);
			return "localisation";
		}
			
		
		return "redirect:/localisation";
	}
	
	@RequestMapping("/getLocalisation")
	public String getLocalisation(Model model, @ModelAttribute("unDocument") Documents documents, 
			@ModelAttribute("unePersonne") Personne personne, @ModelAttribute("uneEntreprise") Entreprise entreprise, String comm) {
		if(cabMetier.findByCommune(comm)==null) {
			model.addAttribute("unDocument", documents);
			model.addAttribute("unePersonne", personne);
			model.addAttribute("uneEntreprise", entreprise);
			model.addAttribute("messageCommune", comm+" n'existe pas");
			return "formulaire";
		}
		String commArrond=cabMetier.findByCommune(comm).getCommuneArrondissement().getLibelleCommuneArrond();
		String dep= cabMetier.findByCommune(comm).getCommuneArrondissement().getDepartement().getLibelleDepartement();
		String reg = cabMetier.findByCommune(comm).getCommuneArrondissement().getDepartement().getRegion().getLibelleRegion();
		model.addAttribute("unDocument", documents);
		model.addAttribute("unePersonne", personne);
		model.addAttribute("uneEntreprise", entreprise);
		model.addAttribute("comm", comm);
		model.addAttribute("commArrond", commArrond);
		model.addAttribute("dep", dep);
		model.addAttribute("reg", reg);
		return "formulaire";
	}
	
	@RequestMapping("/getBeneficiaire")
	public Personne getBeneficiaire(@ModelAttribute("unDocument") Documents documents, Model model, 
			@ModelAttribute("unePersonne") Personne personne,
			@ModelAttribute("uneEntreprise") Entreprise entreprise, Long cni, Long nin, String nomPersonne,
			String prenom, LocalDate dateDelivrance) {
		
		if(cabMetier.chercherPersonne(cni)== null) {
			model.addAttribute("unDocument", documents);
			model.addAttribute("unePersonne", personne);
			model.addAttribute("cni", cni);
			return null;
		}
		nomPersonne = cabMetier.chercherPersonne(cni).getNomPersonne();
		prenom = cabMetier.chercherPersonne(cni).getPrenom();
		nin = cabMetier.chercherPersonne(cni).getNin();
		dateDelivrance = cabMetier.chercherPersonne(cni).getDateDelivrance();
		
		model.addAttribute("unDocument", documents);
		model.addAttribute("unePersonne", personne);
		model.addAttribute("uneEntreprise", entreprise);
		
		model.addAttribute("cni", cni);
		model.addAttribute("nomPersonne", nomPersonne);
		model.addAttribute("prenom", prenom);
		model.addAttribute("nin", nin);
		model.addAttribute("dateDelivrance", dateDelivrance);
		return cabMetier.chercherPersonne(cni);
	}
}
