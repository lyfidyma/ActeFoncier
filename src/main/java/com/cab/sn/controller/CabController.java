package com.cab.sn.controller;


import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
import com.cab.sn.metier.ICabMetier;
import com.cab.sn.metier.StorageService;

import jakarta.validation.Valid;


@Controller
public class CabController {
	
	
	@Autowired
	private ICabMetier cabMetier;
	String messageDoublon = null;
	String messageSucces = null;
	String messageErreur = null;
	String flag = null;
	String flagEntreprise = null;
	@Autowired
	private StorageService storageService;
	@Autowired
	ResourceLoader resourceLoader;
	
	@RequestMapping(value={"/", "/index"})
	public String index() {		
		return "index";
	}
	
	@RequestMapping("/ajouter")
	public String ajouter(Model model, @ModelAttribute("unDocument") Documents documents, 
			@ModelAttribute("unePersonne") Personne personne, @ModelAttribute("uneEntreprise") Entreprise entreprise,
	@ModelAttribute("unTypeDocument") TypeDocument typeDocument, @ModelAttribute("unePieceJointe") PiecesJointes piecesJointes,
	@ModelAttribute("uneCommune") Commune commune, @ModelAttribute("uneCommuneArrondissement") CommuneArrondissement communeArrondissement, 
	@ModelAttribute("unDepartement") Departement departement, @ModelAttribute("uneRegion") Region region){
		List<Commune> listCommune = cabMetier.findAllCommune();
		List<TypeDocument> type = cabMetier.listTypeDocument();
		List<Responsable> listResponsable=cabMetier.findAllResponsable();
		model.addAttribute("listResponsable", listResponsable);
			model.addAttribute("unDocument",documents);	
			model.addAttribute("unePersonne",personne);
			model.addAttribute("uneEntreprise",entreprise);
			model.addAttribute("unTypeDocument",typeDocument);
			model.addAttribute("unePieceJointe",piecesJointes);
			model.addAttribute("uneCommune",commune);
			model.addAttribute("uneCommuneArrondissement",communeArrondissement);
			model.addAttribute("unDepartement",departement);
			model.addAttribute("uneRegion",region);
			model.addAttribute("type",type);	
			model.addAttribute("listCommune", listCommune);
			model.addAttribute("listResponsable", listResponsable);
		return "formulaire";
	}
	
	@RequestMapping("/cons")
	public String consulterTousDoc(@ModelAttribute("unTypeDocument") TypeDocument typeDocument, Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="10")int size,
			String motCle, String keySearch) {
		List<TypeDocument> type = cabMetier.listTypeDocument();
		Page<Documents> pageDocuments=null ;
			if(motCle!=null) {
				pageDocuments = cabMetier.chercherDocuments(motCle, page, size);
				model.addAttribute("listDocuments", pageDocuments);
				model.addAttribute("motCle", motCle);
				model.addAttribute("type",type);
			}
			else if(keySearch!=null) {
				pageDocuments = cabMetier.filtreTypeDocuments(keySearch, page, size);
				model.addAttribute("listDocuments", pageDocuments);
				model.addAttribute("keySearch", keySearch);
				model.addAttribute("type",type);
			}
			else {
				pageDocuments = cabMetier.listDocuments(page, size);
				model.addAttribute("listDocuments", pageDocuments);
				model.addAttribute("motCle", motCle);
				model.addAttribute("type",type);
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
			@ModelAttribute("uneEntreprise") Entreprise entreprise, @Valid @ModelAttribute("unTypeDocument") TypeDocument typeDocument, Errors errorsTypeDocument, 
			@ModelAttribute("unePieceJointe") PiecesJointes piecesJointes, @ModelAttribute("unePieceJointe2") PiecesJointes piecesJointes2,
			@ModelAttribute("uneCommune") Commune commun, @ModelAttribute("uneCommuneArrondissement") CommuneArrondissement communeArrondissement, 
			@ModelAttribute("unDepartement") Departement departement, @ModelAttribute("uneRegion") Region region, 
			Model model, Long idDocument, String numDocument, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  dateDocument,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreation, 
			String titreGlobal,String objetDocument, String statutDocument, 
			String typeBeneficiaire,String responsableDocument, String lot, String nicad, Date dateApprobation, String superficie, String nomEntreprise,
			String ninea, String cni, String nomPersonne, String prenom, String nin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDelivrance, 
			String libelleCommune, String libelleCommuneArrond, String libelleDepartement, String libelleRegion, String typeDoc, 
			String numPj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj, String objetPj,
			String numPj1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj1, String objetPj1, @RequestParam(name="flag",defaultValue="") String  flag,
			@RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1) {
		
		
		if (result.hasErrors()) {
			List<Commune> listCommune = cabMetier.findAllCommune();
			List<TypeDocument> type = cabMetier.listTypeDocument();
				model.addAttribute("type",type);	
				model.addAttribute("listCommune", listCommune);
				if(dateDelivrance!=null)
				model.addAttribute("dateDelivranceString", dateDelivrance.toString());
				if(dateDocument!=null)
				model.addAttribute("dateDocumentString", dateDocument.toString());
				if(datePj!=null)
				model.addAttribute("datePjString", datePj.toString());
				if(datePj1!=null)
					model.addAttribute("datePjString1", datePj1.toString());
				if(typeBeneficiaire.equals("Entreprise"))
					model.addAttribute("flagEntreprise", "entreprise");
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
		
		  if (errors.hasErrors()) {
			  List<Commune> listCommune = cabMetier.findAllCommune();
				List<TypeDocument> type = cabMetier.listTypeDocument();
					model.addAttribute("type",type);	
					model.addAttribute("listCommune", listCommune);
					if(dateDelivrance!=null)
						model.addAttribute("dateDelivranceString", dateDelivrance.toString());
						if(dateDocument!=null)
						model.addAttribute("dateDocumentString", dateDocument.toString());
						if(datePj!=null)
						model.addAttribute("datePjString", datePj.toString());
						if(datePj1!=null)
							model.addAttribute("datePjString1", datePj1.toString());
						if(typeBeneficiaire.equals("Entreprise"))
							model.addAttribute("flagEntreprise", "entreprise");
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
		  if (errorsTypeDocument.hasErrors()) {
			  List<Commune> listCommune = cabMetier.findAllCommune();
				List<TypeDocument> type = cabMetier.listTypeDocument();
					model.addAttribute("type",type);	
					model.addAttribute("listCommune", listCommune);
					if(dateDelivrance!=null)
						model.addAttribute("dateDelivranceString", dateDelivrance.toString());
						if(dateDocument!=null)
						model.addAttribute("dateDocumentString", dateDocument.toString());
						if(datePj!=null)
						model.addAttribute("datePjString", datePj.toString());
						if(datePj1!=null)
							model.addAttribute("datePjString1", datePj1.toString());
						if(typeBeneficiaire.equals("Entreprise"))
							model.addAttribute("flagEntreprise", "entreprise");
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
			
			if(idDocument==null) {
			  String messageDoublon =""; 
		 
			  List <Documents> controleDocument = cabMetier.findByCni(cni);
			  
			  if(cabMetier.findByCni(cni)!=null) { 
				  for(Documents document:controleDocument){ 
					  if(document.getTypeDocument().getTypeDoc().equals(typeDoc) && document.getNumDocument().equals(numDocument) ){
						  if(document.getTypeBeneficiaire().equals("Particulier")){
							  if(document.getPersonne().getCni().equals(cni)) {
								  messageDoublon="Ce document existe sous le numéro "+document.getCodeUniqueDocument(); 
								  model.addAttribute("messageDoublon",messageDoublon); 
								  List<Commune> listCommune = cabMetier.findAllCommune();
									List<TypeDocument> type = cabMetier.listTypeDocument();
										model.addAttribute("type",type);	
										model.addAttribute("listCommune", listCommune);
										if(dateDelivrance!=null)
											model.addAttribute("dateDelivranceString", dateDelivrance.toString());
											if(dateDocument!=null)
											model.addAttribute("dateDocumentString", dateDocument.toString());
											if(datePj!=null)
											model.addAttribute("datePjString", datePj.toString());
											if(datePj1!=null)
												model.addAttribute("datePjString1", datePj1.toString());
								  return "formulaire"; 
			  }
			  
			  }else if (document.getTypeBeneficiaire().equals("Entreprise")) {
				  if(document.getEntreprise().getNinea().equals(ninea)){
					  if(document.getPersonne().getCni().equals(cni)) {
						  messageDoublon="Ce document existe sous le numéro "+document.getCodeUniqueDocument(); 
						  model.addAttribute("messageDoublon", messageDoublon); 
						  List<Commune> listCommune = cabMetier.findAllCommune();
							List<TypeDocument> type = cabMetier.listTypeDocument();
								model.addAttribute("type",type);	
								model.addAttribute("listCommune", listCommune);
								if(dateDelivrance!=null)
									model.addAttribute("dateDelivranceString", dateDelivrance.toString());
									if(dateDocument!=null)
									model.addAttribute("dateDocumentString", dateDocument.toString());
									if(datePj!=null)
									model.addAttribute("datePjString", datePj.toString());
									if(datePj1!=null)
										model.addAttribute("datePjString1", datePj1.toString());
								model.addAttribute("flagEntreprise", "entreprise");
								return "formulaire"; 
								} 
					  } 
				  }
			  
			  } 
					  } 
				  }
			}
			
			
			
			doc = cabMetier.sauvegarderDocuments(idDocument, numDocument, dateDocument, titreGlobal, 
					objetDocument, statutDocument, typeBeneficiaire, responsableDocument, lot, nicad, 
					dateApprobation, superficie, nomEntreprise, ninea, cni, nomPersonne, prenom, nin, dateDelivrance, libelleCommune, typeDoc, 
					numPj, datePj, objetPj, file, numPj1, datePj1, objetPj1, file1);
			
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
	public String modifier(@ModelAttribute("unDocument") Documents doc, 
			@ModelAttribute("unePersonne") Personne personne, @ModelAttribute("uneEntreprise") Entreprise entreprise,
	@ModelAttribute("unTypeDocument") TypeDocument typeDocument, @ModelAttribute("unePieceJointe") PiecesJointes piecesJointes,
	@ModelAttribute("unePieceJointe2") PiecesJointes piecesJointes2,
	@ModelAttribute("uneCommune") Commune commune, @ModelAttribute("uneCommuneArrondissement") CommuneArrondissement communeArrondissement, 
	@ModelAttribute("unDepartement") Departement departement, @ModelAttribute("uneRegion") Region region, Model model, Long id, String typeBeneficiaire  ) {
		
		List<Commune> listCommune = cabMetier.findAllCommune();
		List<TypeDocument> type = cabMetier.listTypeDocument();
		String dateDelivranceString = "jj/mm/yyyy";
		String dateDocumentString = "jj/mm/yyyy";
		
		doc = cabMetier.modifierDocuments(id);
		if(doc.getTypeBeneficiaire().equals("Entreprise")) {
			entreprise=cabMetier.chercherEntreprise(doc.getEntreprise().getIdEntreprise());
			personne = cabMetier.chercherPersonneParId(doc.getPersonne().getIdPersonne());
		}else if(doc.getTypeBeneficiaire().equals("Particulier")) {
			personne = cabMetier.chercherPersonneParId(doc.getPersonne().getIdPersonne());
		}
		
		typeDocument = cabMetier.findByIdTypeDocument(doc.getTypeDocument().getIdTypeDocument());
		commune = cabMetier.findByIdCommune(doc.getCommune().getIdCommune());
		communeArrondissement = cabMetier.findByIdCommuneArrondissement(commune.getCommuneArrondissement().getIdCommuneArrond());
		departement = cabMetier.findByIdDepartement(communeArrondissement.getDepartement().getIdDepartement());
		region = cabMetier.findByIdRegion(departement.getRegion().getIdRegion());
		
		if(cabMetier.modifierDocuments(id).getDateDocument()!=null)
			 dateDocumentString = cabMetier.modifierDocuments(id).getDateDocument().toString();
		Long idbenef = null;
		if(doc.getTypeBeneficiaire().equals("Particulier")) {
			 idbenef = cabMetier.modifierDocuments(id).getPersonne().getIdPersonne();
			if(cabMetier.chercherPersonneParId(idbenef).getDateDelivrance()!=null)
				dateDelivranceString = cabMetier.chercherPersonneParId(idbenef).getDateDelivrance().toString();
		}
		else if(doc.getTypeBeneficiaire().equals("Entreprise")) {
			idbenef = cabMetier.modifierDocuments(id).getEntreprise().getIdEntreprise();
			if(cabMetier.modifierDocuments(id).getPersonne().getDateDelivrance()!=null)
				dateDelivranceString = cabMetier.modifierDocuments(id).getPersonne().getDateDelivrance().toString();
		}
		if(cabMetier.chercherPiecesJointes(id)!=null) {
			if(cabMetier.chercherPiecesJointes(id).size()==1) {
				piecesJointes= cabMetier.chercherPiecesJointes(id).get(0);
				String datePieceJointeString = cabMetier.chercherPiecesJointes(id).get(0).getDatePj().toString();
				String fichier = cabMetier.chercherPiecesJointes(id).get(0).getCheminFichier();
				String nomFichier = resourceLoader.getResource(fichier).getFilename();
				Resource resource = storageService.loadAsResource(nomFichier);
				model.addAttribute("fichierJoint", resource.getFilename());
				model.addAttribute("datePjString", datePieceJointeString);
				model.addAttribute("flag", "unFichier");
				
			}else if(cabMetier.chercherPiecesJointes(id).size()==2) {
				piecesJointes = cabMetier.chercherPiecesJointes(id).get(0);
				piecesJointes2 = cabMetier.chercherPiecesJointes(id).get(1);
				String datePieceJointeString = cabMetier.chercherPiecesJointes(id).get(0).getDatePj().toString();
				String datePieceJointeString1 = cabMetier.chercherPiecesJointes(id).get(1).getDatePj().toString();
				String fichier = cabMetier.chercherPiecesJointes(id).get(0).getCheminFichier();
				String fichier1 = cabMetier.chercherPiecesJointes(id).get(1).getCheminFichier();
				String nomFichier = resourceLoader.getResource(fichier).getFilename();
				String nomFichier1 = resourceLoader.getResource(fichier1).getFilename();
				
				Resource resource = storageService.loadAsResource(nomFichier);
				Resource resource1 = storageService.loadAsResource(nomFichier1);
				
				model.addAttribute("fichierJoint", resource.getFilename());
				model.addAttribute("fichierJoint1", resource1.getFilename());
				model.addAttribute("datePjString", datePieceJointeString);
				model.addAttribute("datePjString1", datePieceJointeString1);
				model.addAttribute("flag", "deuxFichiers");
				
			}
		}
			model.addAttribute("unDocument",doc);
			model.addAttribute("unePersonne",personne);
			model.addAttribute("uneEntreprise",entreprise);
			model.addAttribute("unTypeDocument",typeDocument);
			model.addAttribute("unePieceJointe",piecesJointes);
			model.addAttribute("unePieceJointe2",piecesJointes2);
			model.addAttribute("uneCommune",commune);
			model.addAttribute("uneCommuneArrondissement",communeArrondissement);
			model.addAttribute("unDepartement",departement);
			model.addAttribute("uneRegion",region);
			model.addAttribute("type",type);		
			model.addAttribute("listCommune", listCommune);
			model.addAttribute("dateDocumentString",dateDocumentString);
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
	
	/*
	 * @RequestMapping(value="/afficherDonneesValidation") public String
	 * validerDocumnents(Model
	 * model, @RequestParam(name="codeUniqueDocument",defaultValue="")String
	 * codeUniqueDocument,
	 * 
	 * @RequestParam(name="messageValiderOuRejeter",defaultValue="")String
	 * messageValiderOuRejeter) { String dateDelivranceString = "jj/mm/yyyy"; String
	 * dateDocumentString = "jj/mm/yyyy"; //String
	 * dateApprobationString="jj/mm/yyyy";
	 * 
	 * Documents doc = cabMetier.afficherDonneesValidation(codeUniqueDocument);
	 * 
	 * List <PiecesJointes> pj=
	 * cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument());
	 * if(cabMetier.afficherDonneesValidation(codeUniqueDocument).getDateDocument()!
	 * =null) dateDocumentString =
	 * cabMetier.afficherDonneesValidation(codeUniqueDocument).getDateDocument().
	 * toString(); //if(cabMetier.modifierDocuments(id).getDateApprobation()!=null)
	 * //
	 * dateApprobationString=cabMetier.modifierDocuments(id).getDateApprobation().
	 * toString(); Long idbenef =
	 * cabMetier.afficherDonneesValidation(codeUniqueDocument).getBeneficiaire().
	 * getIdBeneficiaire(); if(doc.getTypeBeneficiaire().equals("Particulier")) {
	 * if(cabMetier.chercherPersonneParId(idbenef).getDateDelivrance()!=null)
	 * dateDelivranceString =
	 * cabMetier.chercherPersonneParId(idbenef).getDateDelivrance().toString(); }
	 * else if(doc.getTypeBeneficiaire().equals("Entreprise")) {
	 * if(cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance()!=
	 * null) dateDelivranceString =
	 * cabMetier.chercherEntreprise(idbenef).getPersonne().getDateDelivrance().
	 * toString(); }
	 * if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument())!=null) {
	 * if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument()).size()==1) { String
	 * datePieceJointeString =
	 * cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString();
	 * model.addAttribute("datePieceJointeString", datePieceJointeString); }else
	 * if(cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument()).size()==2) { String
	 * datePieceJointeString =
	 * cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString(); String
	 * datePieceJointeString1 =
	 * cabMetier.chercherPiecesJointes(cabMetier.afficherDonneesValidation(
	 * codeUniqueDocument).getIdDocument()).get(0).getDatePj().toString();
	 * model.addAttribute("datePieceJointeString", datePieceJointeString);
	 * model.addAttribute("datePieceJointeString1", datePieceJointeString1); } }
	 * 
	 * model.addAttribute("unDocument",doc);
	 * 
	 * model.addAttribute("piecesJointes", pj);
	 * model.addAttribute("dateDocumentString",dateDocumentString);
	 * //model.addAttribute("dateApprobationString",dateApprobationString);
	 * model.addAttribute("dateDelivranceString", dateDelivranceString);
	 * model.addAttribute("messageValiderOuRejeter", messageValiderOuRejeter);
	 * 
	 * 
	 * return "validation"; }
	 */
	
	@RequestMapping(value= "/pageValidation")
	public String pageValidation(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size,
			String motCle, @RequestParam(name="flag", defaultValue="") String flag) {
		
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
				if(flag.isEmpty()==false)
					model.addAttribute("flag", flag);
				
				model.addAttribute("currentPage", page);
				model.addAttribute("size", size);
				model.addAttribute("totalItems", pageDocuments.getTotalElements());
				model.addAttribute("totalPages", pageDocuments.getTotalPages());
		return "validation";
	}
	
	@RequestMapping(value="/valider", method=RequestMethod.POST)
	public String valider(Model model, Long idDoc, String checkboxValider, String checkboxRejeter, String nomApprobateur,
			String prenomApprobateur, String motifRejet, MultipartFile file) {
		
			cabMetier.validerDocuments(idDoc, checkboxValider, checkboxRejeter, nomApprobateur, prenomApprobateur,
					motifRejet, file);
				if(checkboxValider.equals("true"))
					flag = "Valider";
				if(checkboxRejeter.equals("true"))
					flag = "Rejeter";
				
		return "redirect:/pageValidation?flag="+flag;
	}
	@RequestMapping(value={"/beneficiaire"})
	public String beneficiaire(@ModelAttribute("unePersonne") Personne unePersonne, 
			@ModelAttribute("uneEntreprise") Entreprise uneEntreprise,
			Model model, @RequestParam(name="messageSucces", defaultValue="") String messageSucces,
			@RequestParam(name="messageErreur", defaultValue="") String messageErreur,
			@RequestParam(name="messageDoublon", defaultValue="") String messageDoublon,
			@RequestParam(name="flag", defaultValue="") String flag, 
			@RequestParam(name="flagEntreprise", defaultValue="") String flagEntreprise){	
		List<Personne> listPersonne = cabMetier.findAllPersonne();
		List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
		model.addAttribute("listPersonne", listPersonne);
		model.addAttribute("listEntreprise", listEntreprise);
		if(messageSucces.isEmpty()==false)
			model.addAttribute("messageSucces", messageSucces);
		if(messageErreur.isEmpty()==false)
			model.addAttribute("messageErreur", messageErreur);
		if(messageDoublon.isEmpty()==false)
			model.addAttribute("messageDoublon", messageDoublon);
		if(flag.isEmpty()==false)
			model.addAttribute("flag", flag);
		if(flagEntreprise.isEmpty()==false)
			model.addAttribute("flagEntreprise", flagEntreprise);
		return "beneficiaire";
	}
	
	@RequestMapping("/referentielBeneficiaire")
	public String ajoutBeneficiaire(@ModelAttribute ("unePersonne") Personne personne, @ModelAttribute ("uneEntreprise") Entreprise entreprise,
			Model model, String typeBeneficiaire, Long idPersonne, String nomPersonne, String prenom, String cni, String nin, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateDelivrance, Long idEntreprise, String nomEntreprise, String ninea, 
			boolean checkbox) {
		
		String messageCni = "";
		String messageNin = "";
		String regex = "^[1-2]\\d{12}$";
		String regexCEDEAO = "^[1-2]\\d{16}$";
		Pattern pattern = Pattern.compile(regex);
		Pattern patternCEDEAO = Pattern.compile(regexCEDEAO);
		
		if(typeBeneficiaire.equals("Particulier")) {
			
		if(nomPersonne.isBlank() || prenom.isBlank() || cni.isBlank() || nin.isBlank() || dateDelivrance == null) {
			model.addAttribute("messageErreur", "Tous les champs sont obligatoires");
			 model.addAttribute("nomPersonne", nomPersonne);
			 model.addAttribute("prenom", prenom);
			 model.addAttribute("cni", cni);
			 model.addAttribute("nin", nin);
			 model.addAttribute("dateDelivrance", dateDelivrance);
			 List<Personne> listPersonne = cabMetier.findAllPersonne();
				List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
				model.addAttribute("listPersonne", listPersonne);
				model.addAttribute("listEntreprise", listEntreprise);
			return "beneficiaire";
		}
		
		 
		 if(cni!=null) {
		 Matcher matcher = pattern.matcher(cni.toString());
		 if (matcher.find() == false) {
			 messageCni = "La CNI doit comporter 13 chiffres";
			 model.addAttribute("messageCni", messageCni);
			 model.addAttribute("nomPersonne", nomPersonne);
			 model.addAttribute("prenom", prenom);
			 model.addAttribute("cni", cni);
			 model.addAttribute("nin", nin);
			 model.addAttribute("dateDelivrance", dateDelivrance);
			 List<Personne> listPersonne = cabMetier.findAllPersonne();
				List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
				model.addAttribute("listPersonne", listPersonne);
				model.addAttribute("listEntreprise", listEntreprise);
			 return "beneficiaire"; 
		 }
	}
		 
		 if(nin!=null) {
			 Matcher matcherCEDEAO = patternCEDEAO.matcher(nin.toString());
			 if (matcherCEDEAO.find() == false) {
				 messageNin = "Le numéro CEDEAO doit comporter 17 chiffres";
				 model.addAttribute("messageNin", messageNin);
				 model.addAttribute("nomPersonne", nomPersonne);
				 model.addAttribute("prenom", prenom);
				 model.addAttribute("cni", cni);
				 model.addAttribute("nin", nin);
				 model.addAttribute("dateDelivrance", dateDelivrance);
				 List<Personne> listPersonne = cabMetier.findAllPersonne();
					List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
					model.addAttribute("listPersonne", listPersonne);
					model.addAttribute("listEntreprise", listEntreprise);
				 return "beneficiaire"; 
			 }
				 
		 }
		 
		if(idPersonne==null) {
	if (cabMetier.chercherPersonne(cni)!= null) {
		model.addAttribute("messageDoublon", "Cette personne existe déjà");
		 model.addAttribute("messageCni", messageCni);
		 model.addAttribute("nomPersonne", nomPersonne);
		 model.addAttribute("prenom", prenom);
		 model.addAttribute("cni", cni);
		 model.addAttribute("nin", nin);
		 model.addAttribute("dateDelivrance", dateDelivrance);
		 List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);
		return "beneficiaire";
	}
} 
	personne = cabMetier.ajoutPersonne(idPersonne, nomPersonne, prenom, cni, nin, dateDelivrance);
	List<Personne> listPersonne = cabMetier.findAllPersonne();
	List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
	model.addAttribute("listPersonne", listPersonne);
	model.addAttribute("listEntreprise", listEntreprise);
	messageSucces = cni+ " enregistré";
	return "redirect:/beneficiaire?messageSucces="+messageSucces;
	

}
		
		if(typeBeneficiaire.equals("Entreprise")) {
				if(nomEntreprise.isBlank() || ninea.isBlank()) {
				model.addAttribute("messageErreur", "Tous les champs sont obligatoires");
				model.addAttribute("nomEntreprise", nomEntreprise);
				model.addAttribute("ninea", ninea);
				model.addAttribute("typeBeneficiaire", typeBeneficiaire);
				List<Personne> listPersonne = cabMetier.findAllPersonne();
				List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
				model.addAttribute("listPersonne", listPersonne);
				model.addAttribute("listEntreprise", listEntreprise);
				model.addAttribute("flagEntreprise", "Erreur");
				return "beneficiaire";
			}
				
				if(idEntreprise==null) {
			if(ninea!=null) {
			if(cabMetier.chercherEntrepriseParNinea(ninea)!= null) {
				model.addAttribute("messageDoublon", "Cette entreprise existe déjà");
				model.addAttribute("nomEntreprise", nomEntreprise);
				 model.addAttribute("ninea", ninea);
				 model.addAttribute("typeBeneficiaire", typeBeneficiaire);
				 List<Personne> listPersonne = cabMetier.findAllPersonne();
					List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
					model.addAttribute("listPersonne", listPersonne);
					model.addAttribute("listEntreprise", listEntreprise);
					model.addAttribute("flagEntreprise", "Erreur");
				return "beneficiaire";
			}
		}
				}
			
			entreprise = cabMetier.ajoutEntreprise(idEntreprise, nomEntreprise, ninea);
			
			List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);
			messageSucces = nomEntreprise+" enregistrée";
			flagEntreprise = "Succes";
			return "redirect:/beneficiaire?messageSucces="+messageSucces+"&flagEntreprise="+flagEntreprise;
		}
		
		return "redirect:/beneficiaire";
	}
	
	
	@RequestMapping("/typededocument")
	public String typeDeDocument(@ModelAttribute("unTypeDocument") TypeDocument unTypeDocument, Model model,
			@RequestParam(name="messageSucces", defaultValue="") String messageSucces,
			@RequestParam(name="messageErreur", defaultValue="") String messageErreur,
			@RequestParam(name="messageDoublon", defaultValue="") String messageDoublon,
			@RequestParam(name="flag", defaultValue="") String flag) {
		List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
		model.addAttribute("unTypeDocument", unTypeDocument);
		model.addAttribute("listTypeDocument", listTypeDocument);
		if(messageSucces.isEmpty()==false)
			model.addAttribute("messageSucces", messageSucces);
		if(messageErreur.isEmpty()==false)
			model.addAttribute("messageErreur", messageErreur);
		if(messageDoublon.isEmpty()==false)
			model.addAttribute("messageDoublon", messageDoublon);
		if(flag.isEmpty()==false)
			model.addAttribute("flag", flag);
		return "typededocument";
	}
	
	@RequestMapping("/referentielTypeDocument")
	public String ajoutTypeDocument(Model model, @ModelAttribute("unTypeDocument") TypeDocument unTypeDocument, Long idTypeDocument, String typeDoc) {
		if(typeDoc.isBlank()) {
			
			
			model.addAttribute("typeDoc", typeDoc);
			List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
			model.addAttribute("listTypeDocument", listTypeDocument);
			messageErreur = "Donner un type";
			return "redirect:/typededocument?messageErreur="+messageErreur;
		}
		
		if(cabMetier.chercherTypeDocumentParTypeDocument(typeDoc)!=null) {
			
		
			model.addAttribute("typeDoc", typeDoc);
			List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
			model.addAttribute("listTypeDocument", listTypeDocument);
			messageDoublon = cabMetier.chercherTypeDocumentParTypeDocument(typeDoc).getTypeDoc()+" existe déjà";
			return "redirect:/typededocument?messageDoublon="+messageDoublon;
		}
		
		unTypeDocument=cabMetier.ajoutTypeDocument(idTypeDocument, typeDoc);
		List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
		
		model.addAttribute("listTypeDocument", listTypeDocument);
		messageSucces = typeDoc+" enregistré(e)";
		return "redirect:/typededocument?messageSucces="+messageSucces;
	}
	
	@RequestMapping("/localisation")
	public String localisation(@ModelAttribute("uneCommune") Commune uneCommune, 
			@ModelAttribute("uneCommuneArrondissement") CommuneArrondissement uneCommuneArrondissement,
			@ModelAttribute("unDepartement") Departement unDepartement,
			@ModelAttribute("uneRegion") Region uneRegion, Model model, 
			@RequestParam(name="messageSucces", defaultValue="") String messageSucces,
			@RequestParam(name="messageErreur", defaultValue="") String messageErreur,
			@RequestParam(name="flag", defaultValue="") String flag) {
		
		List<Commune> listCommune = cabMetier.findAllCommune();
		model.addAttribute("listCommune", listCommune);
		if(messageSucces.isEmpty()==false)
			model.addAttribute("messageSucces", messageSucces);
		if(messageErreur.isEmpty()==false)
			model.addAttribute("messageErreur", messageErreur);
		if(flag.isEmpty()==false)
			model.addAttribute("flag", flag);
		return "localisation";
	}
	
	@RequestMapping("/referentielLocalisation")
	public String ajoutLocalisation(@ModelAttribute("uneCommune") Commune uneCommune, 
			@ModelAttribute("uneCommuneArrondissement") CommuneArrondissement uneCommuneArrondissement,
			@ModelAttribute("unDepartement") Departement unDepartement,
			@ModelAttribute("uneRegion") Region uneRegion, Model model, String libelleCommune, 
			String libelleCommuneArrond, String libelleDepartement, String libelleRegion ) {
		
		if(libelleCommune.isBlank() && libelleCommuneArrond.isBlank() && libelleDepartement.isBlank() && libelleRegion.isBlank()) {
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			model.addAttribute("messageErreur", "Tous les champs sont obligatoires");
			
		
		}
		if(libelleCommune.isBlank()==false && (libelleCommuneArrond.isBlank() || libelleDepartement.isBlank() || libelleRegion.isBlank())) {
			if(cabMetier.findByCommune(libelleCommune)!=null) {
				model.addAttribute("messageDoublon", libelleCommune+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				
			
			}else {
			model.addAttribute("messageErreur", "Renseigner la commune d'arrondissement, le département et la région");
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
		return "localisation";
			}
		}
		if(libelleCommuneArrond.isBlank()==false && (libelleDepartement.isBlank() || libelleRegion.isBlank())) {
			if(cabMetier.findByCommuneArrondissement(libelleCommuneArrond) != null) {
				model.addAttribute("messageDoublon", libelleCommuneArrond+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
			return "localisation";
				
			} else {
			model.addAttribute("messageErreur", "Renseigner le département et la région");
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
		return "localisation";
			}
		}
		if(libelleDepartement.isBlank()== false && libelleRegion.isBlank()) {
			if(cabMetier.findByDepartement(libelleDepartement) != null) {
				model.addAttribute("messageDoublon", libelleDepartement+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
			return "localisation";
			}else {
			model.addAttribute("messageErreur", "Renseigner la région");
			
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
		return "localisation";
			}
		} else if(libelleDepartement.isBlank()== false && libelleRegion.isBlank()== false && (libelleCommune.isBlank() && libelleCommuneArrond.isBlank())) {
		
			if(cabMetier.findByDepartement(libelleDepartement) != null) {
				model.addAttribute("messageDoublon", libelleDepartement+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				return "localisation";	
			}
			
			cabMetier.ajoutDepartement(libelleDepartement, libelleRegion);
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			messageSucces = libelleDepartement+" enregistré";
			return "redirect:/localisation?messageSucces="+messageSucces;
		}
		
		if((libelleCommune.isBlank() && libelleCommuneArrond.isBlank() && libelleDepartement.isBlank() && libelleRegion.isBlank()==false)) {
			if(cabMetier.findByRegion(libelleRegion)!= null) {
				model.addAttribute("messageDoublon", libelleRegion+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
			return "localisation";
		}else {
			cabMetier.ajoutRegion(libelleRegion);
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			messageSucces = libelleRegion+" enregistrée";
			return "redirect:/localisation?messageSucces="+messageSucces;
		}
		}
		
		if(libelleCommune.isBlank() && libelleCommuneArrond.isBlank()==false && libelleDepartement.isBlank()==false && libelleRegion.isBlank()==false) {
			if(cabMetier.findByCommuneArrondissement(libelleCommuneArrond)!=null) {
				model.addAttribute("messageDoublon", libelleCommuneArrond+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				return "localisation";	
			}
			cabMetier.ajoutCommuneArrondissement(libelleCommuneArrond, libelleDepartement, libelleRegion);
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			messageSucces = libelleCommuneArrond+" enregistrée";
			return "redirect:/localisation?messageSucces="+messageSucces;
		}
		
		if(libelleCommune.isBlank()==false && libelleCommuneArrond.isBlank()==false && libelleDepartement.isBlank()==false && libelleRegion.isBlank()==false) {
						
			if(cabMetier.findByCommune(libelleCommune)!=null) {
				model.addAttribute("messageDoublon", libelleCommune+" existe déjà");
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				return "localisation";	
			}
			
			cabMetier.ajoutCommune(libelleCommune, libelleCommuneArrond, libelleDepartement, libelleRegion);
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			messageSucces = libelleCommune+" enregistrée";
			return  "redirect:/localisation?messageSucces="+messageSucces;
		}
			
		List<Commune> listCommune = cabMetier.findAllCommune();
		model.addAttribute("listCommune", listCommune);
		return "localisation";
	}
	
	@RequestMapping("/getLocalisation")
	@ResponseBody
	public Object getLocalisation(Model model, String comm) {
		if(cabMetier.findByCommune(comm)==null) {
			return null;
		}
		Map<String, Object> object = new HashMap<>();
		object.put("commArrond", cabMetier.findByCommune(comm).getCommuneArrondissement().getLibelleCommuneArrond());
		object.put("dep", cabMetier.findByCommune(comm).getCommuneArrondissement().getDepartement().getLibelleDepartement());
		object.put("reg", cabMetier.findByCommune(comm).getCommuneArrondissement().getDepartement().getRegion().getLibelleRegion());
		
		return object;
	}
	
	@RequestMapping("/getPersonne")
	@ResponseBody
	public Object getPersonne(String cni) {	
		if(cabMetier.chercherPersonne(cni)== null) {
			return null;
		}
		Map<String, Object> object = new HashMap<>();
		object.put("nomPersonne", cabMetier.chercherPersonne(cni).getNomPersonne());
		object.put("prenom", cabMetier.chercherPersonne(cni).getPrenom());
		object.put("nin", cabMetier.chercherPersonne(cni).getNin());
		object.put("dateDelivrance", cabMetier.chercherPersonne(cni).getDateDelivrance());
		
		return object;
	}
	
	@RequestMapping("/getEntreprise")
	@ResponseBody
	public Object getEntreprise( String ninea) {	
		if(cabMetier.chercherEntrepriseParNinea(ninea)== null) {
			return null;
		}
		Map<String, Object> object = new HashMap<>();
		object.put("nomEntreprise", cabMetier.chercherEntrepriseParNinea(ninea).getNomEntreprise());
		
		return object;
	}
	@RequestMapping(value="/supprimerPersonne", method=RequestMethod.GET)
	public String deletePersonne(@ModelAttribute("unePersonne") Personne unePersonne, 
			@ModelAttribute("uneEntreprise") Entreprise uneEntreprise, Model model, Long id) {
		
		List <Documents> documents= new ArrayList<>();
		documents=cabMetier.findByCni(cabMetier.chercherPersonneParId(id).getCni());
		
		if(documents.isEmpty()==false) {
			List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("uneEntreprise", uneEntreprise);
			model.addAttribute("unePersonne", unePersonne);
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);
			messageErreur = cabMetier.chercherPersonneParId(id).getCni()+"  est lié à un document.";
			flag = "personne";
			return "redirect:/beneficiaire?messageErreur="+messageErreur+"&flag="+flag;
		}
		String nomPersonneASupprimer = cabMetier.chercherPersonneParId(id).getNomPersonne();
		String prenomPersonneASupprimer = cabMetier.chercherPersonneParId(id).getPrenom();
		cabMetier.supprimerPersonne(id);
		List<Personne> listPersonne = cabMetier.findAllPersonne();
		List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
		model.addAttribute("listPersonne", listPersonne);
		model.addAttribute("listEntreprise", listEntreprise);
		messageSucces = nomPersonneASupprimer+" "+ prenomPersonneASupprimer+"  à été supprimé(e)" ;
		flag = "personne";
		return "redirect:/beneficiaire?messageSucces="+messageSucces+"&flag="+flag;
	}	
	
	@RequestMapping(value="/supprimerEntreprise", method=RequestMethod.GET)
	public String deleteEntreprise(@ModelAttribute("unePersonne") Personne unePersonne, @ModelAttribute("uneEntreprise") Entreprise uneEntreprise,
			Model model, Long id) {
		List <Documents> documents= new ArrayList<>();
		String nineaASupprimer=cabMetier.chercherEntreprise(id).getNinea();
		documents=cabMetier.findByNinea(nineaASupprimer);
		
		if(documents.isEmpty()==false) {
			List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("uneEntreprise", uneEntreprise);
			model.addAttribute("unePersonne", unePersonne);
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);
			messageErreur = cabMetier.chercherEntreprise(id).getNomEntreprise()+" est liée à un document.";
			flag =  "entreprise";
			return "redirect:/beneficiaire?messageErreur="+messageErreur+"&flag="+flag;
		}
		String entrepriseASupprimer = cabMetier.chercherEntreprise(id).getNomEntreprise();
		cabMetier.supprimerEntreprise(id);
		List<Personne> listPersonne = cabMetier.findAllPersonne();
		List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
		model.addAttribute("listPersonne", listPersonne);
		model.addAttribute("listEntreprise", listEntreprise);
		messageSucces = entrepriseASupprimer+" "+"à été supprimée";
		flag =  "entreprise";
		return "redirect:/beneficiaire?messageSucces="+messageSucces+"&flag="+flag;
	}	
	
	@RequestMapping(value="/modifierPersonne", method=RequestMethod.GET)
	public String modifierPersonne(@ModelAttribute("unDocument") Documents doc, 
			@ModelAttribute("unePersonne") Personne unePersonne, @ModelAttribute("uneEntreprise") Entreprise uneEntreprise, Model model, Long id) {
			unePersonne= cabMetier.chercherPersonneParId(id);
			model.addAttribute("unePersonne", unePersonne);
			List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);
			model.addAttribute("dateDelivranceString", unePersonne.getDateDelivrance().toString());
		
			
		return "beneficiaire";
	}
	
	@RequestMapping(value="/modifierEntreprise", method=RequestMethod.GET)
	public String modifierEntreprise(@ModelAttribute("unDocument") Documents doc, @ModelAttribute("unePersonne") Personne unePersonne,
			@ModelAttribute("uneEntreprise") Entreprise uneEntreprise, Model model, Long id) {
			uneEntreprise= cabMetier.chercherEntreprise(id);
			model.addAttribute("uneEntreprise", uneEntreprise);
			List<Personne> listPersonne = cabMetier.findAllPersonne();
			List<Entreprise> listEntreprise = cabMetier.findAllEntreprise();
			model.addAttribute("listPersonne", listPersonne);
			model.addAttribute("listEntreprise", listEntreprise);	
			model.addAttribute("flagEntreprise", "Entreprise");
		return "beneficiaire";
	}
	
	@RequestMapping(value="/supprimerTypeDocument", method=RequestMethod.GET)
	public String deleteTypeDocument(@ModelAttribute("unTypeDocument") TypeDocument unTypeDocument, Model model, Long id) {
		List <Documents> documents= new ArrayList<>();
		documents=cabMetier.findByTypeDocument(cabMetier.findByIdTypeDocument(id).getTypeDoc());
		
		if(documents.isEmpty()==false) {
			List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
			model.addAttribute("unTypeDocument", unTypeDocument);
			model.addAttribute("listTypeDocument", listTypeDocument);
			messageErreur = cabMetier.findByIdTypeDocument(id).getTypeDoc()+" est lié à un document.";
			flag = "Suppression";
			return "redirect:/typededocument?messageErreur="+messageErreur+"&flag="+flag;
		}
		String typeDocASupprimer = cabMetier.findByIdTypeDocument(id).getTypeDoc();
		cabMetier.supprimerTypeDocument(id);
		List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
		model.addAttribute("unTypeDocument", unTypeDocument);
		model.addAttribute("listTypeDocument", listTypeDocument);
		messageSucces = typeDocASupprimer+" à été supprimé";
		flag ="Suppression";
		return "redirect:/typededocument?messageSucces="+messageSucces+"&flag="+flag;
	}
	
	@RequestMapping(value="/modifierTypeDocument", method=RequestMethod.GET)
	public String modifierTypeDocument(@ModelAttribute("unTypeDocument") TypeDocument unTypeDocument, Model model, Long id) {
		
		unTypeDocument = cabMetier.findByIdTypeDocument(id);
			model.addAttribute("unTypeDocument", unTypeDocument);
			List<TypeDocument> listTypeDocument = cabMetier.listTypeDocument();
			model.addAttribute("listTypeDocument", listTypeDocument);
			
		return "typededocument";
	}
	
	@RequestMapping(value="/supprimerLocalisation", method=RequestMethod.GET)
	public String deleteLocalisation(@ModelAttribute("uneCommune") Commune uneCommune, 
			@ModelAttribute("uneCommuneArrondissement") CommuneArrondissement uneCommuneArrondissement,
			@ModelAttribute("unDepartement") Departement unDepartement,
			@ModelAttribute("uneRegion") Region uneRegion, Model model, Long id) {
		
		List <Documents> documents= new ArrayList<>();
		documents=cabMetier.findDocumentsByCommune(cabMetier.findByIdCommune(id).getLibelleCommune());
		
		if(documents.isEmpty()==false) {
			
			model.addAttribute("uneCommune", uneCommune);
			List<Commune> listCommune = cabMetier.findAllCommune();
			model.addAttribute("listCommune", listCommune);
			messageErreur = cabMetier.findByIdCommune(id).getLibelleCommune()+" est lié à un document.";
			flag="Erreur";
			return "redirect:/localisation?messageErreur="+messageErreur+"&flag="+flag;
		}
		String communeASupprimer=cabMetier.findByIdCommune(id).getLibelleCommune();
		cabMetier.supprimerCommune(id);
		List<Commune> listCommune = cabMetier.findAllCommune();
		model.addAttribute("listCommune", listCommune);
		messageSucces = communeASupprimer+" à été supprimée";
		flag = "Succes";
		return "redirect:/localisation?messageSucces="+messageSucces+"&flag="+flag;
	}	
	
	@RequestMapping(value="/modifierLocalisation")
	public String modifierLocalisation(@ModelAttribute("uneCommune") Commune uneCommune, 
			@ModelAttribute("uneCommuneArrondissement") CommuneArrondissement uneCommuneArrondissement,
			@ModelAttribute("unDepartement") Departement unDepartement,
			@ModelAttribute("uneRegion") Region uneRegion,
			
			Model model, String libelleCommune, String libelleCommuneArrond, 
			String libelleDepartement, String libelleRegion, String communeModifier,
			String communeArrondModifier, String departementModifier, String regionModifier) {
			
		if(communeModifier.isBlank()==false ){
			if(cabMetier.findByCommune(libelleCommune)==null) {
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				model.addAttribute("messageErreur", libelleCommune+"  n'existe pas");
				model.addAttribute("flag", "Erreur");
				
				return "localisation";
			}else {
				cabMetier.modifierCommune(libelleCommune, communeModifier);
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				messageSucces = libelleCommune+" a été modifiée";
				flag="Succes";
				return "redirect:/localisation?messageSucces="+messageSucces+"&flag="+flag;
			}
		}
		
		if(communeArrondModifier.isBlank()==false) {
			if(cabMetier.findByCommuneArrondissement(libelleCommuneArrond)==null) {
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				model.addAttribute("messageErreur", libelleCommuneArrond+" n'existe pas");
				model.addAttribute("flag", "Erreur");
				return "localisation";
			}else {
				cabMetier.modifierCommuneArrondissement(libelleCommuneArrond, communeArrondModifier);
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				messageSucces = libelleCommuneArrond+" a été modifiée";
				flag="Succes";
				return "redirect:/localisation?messageSucces="+messageSucces+"&flag="+flag;
			}
		}
		if(departementModifier.isBlank()==false) {
			if(cabMetier.findByDepartement(libelleDepartement)==null) {
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				model.addAttribute("messageErreur", libelleDepartement+"  n'existe pas");
				model.addAttribute("flag", "Erreur");
				return "localisation";
			}else {
				cabMetier.modifierDepartement(libelleDepartement, departementModifier);
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				messageSucces = libelleDepartement+" a été modifié";
				flag="Succes";
				return "redirect:/localisation?messageSucces="+messageSucces+"&flag="+flag;
			}
		}
		if(regionModifier.isBlank()==false) {
			if(cabMetier.findByRegion(libelleRegion)==null) {
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				model.addAttribute("messageErreur", libelleRegion+"  n'existe pas");
				model.addAttribute("flag", "Erreur");
				return "localisation";
			}else {
				cabMetier.modifierRegion(libelleRegion, regionModifier);
				List<Commune> listCommune = cabMetier.findAllCommune();
				model.addAttribute("listCommune", listCommune);
				messageSucces = libelleRegion+" a été modifiée";
				flag="Succes";
				return "redirect:/localisation?messageSucces="+messageSucces+"&flag="+flag;
				}
		}
			
		List<Commune> listCommune = cabMetier.findAllCommune();
		model.addAttribute("listCommune", listCommune);
		return "localisation";
		
	}
	
	@RequestMapping("/responsable")
	public String responsableDocument(@ModelAttribute ("unResponsable") Responsable unResponsable, Model model,
			@RequestParam(name="messageSucces", defaultValue="") String messageSucces, 
			@RequestParam(name="flag", defaultValue="") String flag){
		
		List<Responsable> listResponsable=cabMetier.findAllResponsable();
		model.addAttribute("unResponsable", unResponsable);
		model.addAttribute("listResponsable", listResponsable);
		if(messageSucces.isEmpty()==false)
				model.addAttribute("messageSucces", messageSucces);
		if(flag.isEmpty()==false)
			model.addAttribute("flag", flag);
		return "responsable";
	}
	
	@RequestMapping("/referentielResponsable")
	public String ajoutResponsable(@Valid @ModelAttribute("unResponsable") Responsable unResponsable, Errors errors, Model model, 
			Long idResponsable, String nomResponsable, String prenomResponsable, String fonction) {
		
		if(errors.hasErrors()) {
			List<Responsable> listResponsable=cabMetier.findAllResponsable();
			model.addAttribute("listResponsable", listResponsable);
			return "responsable";
		}
		
		cabMetier.ajoutResponsable(idResponsable, nomResponsable, prenomResponsable, fonction);
		List<Responsable> listResponsable=cabMetier.findAllResponsable();
		model.addAttribute("listResponsable", listResponsable);
		messageSucces = nomResponsable+" "+prenomResponsable+" a été enregistré(e)";
		byte[] utf8 = messageSucces.getBytes();
		try {
			byte[] latin1 = new String(utf8, "UTF-8").getBytes("ISO-8859-1");
			System.out.println(latin1+"OOOOOOOOOOOOOOOOOOOOOOO");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/responsable?messageSucces="+messageSucces;
	}
	
	@RequestMapping("/supprimerResponsable")
	public String supprimerResponsable(@ModelAttribute("unResponsable") Responsable unResponsable, Model model, Long id) {
		
		String nomRespASupprimer=cabMetier.findByIdResponsable(id).getNomResponsable();
		String prenomRespASupprimer=cabMetier.findByIdResponsable(id).getPrenomResponsable();
		cabMetier.supprimerResponsable(id);
		List<Responsable> listResponsable=cabMetier.findAllResponsable();
		model.addAttribute("listResponsable", listResponsable);
		messageSucces = prenomRespASupprimer+" "+ nomRespASupprimer+" a été supprimé(e)";
		flag = "Succes";
		return "redirect:/responsable?messageSucces="+messageSucces+"&flag="+flag;
		
	}
	
	@RequestMapping("/modifierResponsable")
	public String modifierResponsable(@ModelAttribute("unResponsable") Responsable unResponsable, Model model, Long id) {
		unResponsable = cabMetier.findByIdResponsable(id);
		List<Responsable> listResponsable=cabMetier.findAllResponsable();
		model.addAttribute("unResponsable", unResponsable);
		model.addAttribute("listResponsable", listResponsable);
		return "responsable";
	}
	
	@RequestMapping("/utilisateur")
	public String utilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, Errors errors, 
		@ModelAttribute("unProfil") Profil unProfil, Model model, 
		@RequestParam(name="messageSucces",defaultValue="") String  messageSucces,
		@RequestParam(name="flag",defaultValue="") String  flag) {
		List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("unUtilisateur", unUtilisateur);
		model.addAttribute("unProfil", unProfil);
		model.addAttribute("listProfil", listProfil);
		model.addAttribute("listUtilisateur", listUtilisateur);
		if(messageSucces.isEmpty()==false)
			model.addAttribute("messageSucces", messageSucces);
		if(flag.isEmpty()==false)
			model.addAttribute("flag", flag);
		return "utilisateur";
	}
	
	@RequestMapping("/referentielUtilisateur")
	public String ajoutUtilisateur(@Valid @ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, Errors errors, 
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long idUtilisateur, String nomUtilisateur, 
			String prenomUtilisateur, String email, String password, String nomProfil) {
		
		if(errors.hasErrors()) {
			List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
			model.addAttribute("listUtilisateur", listUtilisateur);
			List<Profil> listProfil=cabMetier.findAllProfil();
			model.addAttribute("listProfil", listProfil);
			return "utilisateur";
		}
		
		if(idUtilisateur==null) {
			if(cabMetier.findByEmail(email).isEmpty()==false) {
				
				List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
				model.addAttribute("listUtilisateur", listUtilisateur);
				List<Profil> listProfil=cabMetier.findAllProfil();
				model.addAttribute("listProfil", listProfil);
				model.addAttribute("messageDoublon", email+" existe déjà");
				return "utilisateur";
			}
	}
		
		cabMetier.ajoutUtilisateur(idUtilisateur, nomUtilisateur, prenomUtilisateur, email, password, nomProfil);
		List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
		model.addAttribute("unUtilisateur", unUtilisateur);
		model.addAttribute("listUtilisateur", listUtilisateur);
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		messageSucces = nomUtilisateur+" "+prenomUtilisateur+" a été enregistré(e)";
		return "redirect:/utilisateur?messageSucces="+messageSucces;
		
	}
	
	@RequestMapping("/supprimerUtilisateur")
	public String supprimmerUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, 
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long id) {
		String nomutASupprimer = cabMetier.findByIdUtilisateur(id).getNomUtilisateur();
		String prenomutASupprimer = cabMetier.findByIdUtilisateur(id).getPrenomUtilisateur();
	
		cabMetier.supprimerUtilisateur(id);
		List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
		model.addAttribute("listUtilisateur", listUtilisateur);
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		messageSucces = nomutASupprimer+" "+prenomutASupprimer+" a été supprimé(e)";	
		flag= "Succes";
		
		return "redirect:/utilisateur?messageSucces="+messageSucces+"&flag="+flag;
	}
	
	@RequestMapping("/modifierUtilisateur")
	public String modifierUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, 
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long id) {
		unUtilisateur = cabMetier.findByIdUtilisateur(id);
		model.addAttribute("unUtilisateur", unUtilisateur);
		List<Utilisateur> listUtilisateur = cabMetier.findAllUtilisateur();
		model.addAttribute("listUtilisateur", listUtilisateur);
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		return "utilisateur";
	}
	
	@RequestMapping("/profil")
	public String profilUtilisateur(@ModelAttribute ("unProfil") Profil unProfil, Model model,
			@RequestParam(name="messageDoublon",defaultValue="") String  messageDoublon,
			@RequestParam(name="messageSucces",defaultValue="") String  messageSucces){
		
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		model.addAttribute("unProfil", unProfil);
		if(messageDoublon.isEmpty()==false)
		model.addAttribute("messageDoublon", messageDoublon);
		if(messageSucces.isEmpty()==false)
		model.addAttribute("messageSucces", messageSucces);
		
		return "profil";
	}
	
	@RequestMapping("/referentielProfil")
	public String ajoutProfil(@ModelAttribute("unProfil") Profil unProfil, Model model, String profil) {
		if(cabMetier.findByProfil(profil)!=null) {
			String messageDoublon=null;
			List<Profil> listProfil=cabMetier.findAllProfil();
			model.addAttribute("listProfil", listProfil);
			messageDoublon=profil+" existe déjà";
			return "redirect:/profil?messageDoublon="+messageDoublon;
			
		}
		String messageSucces = null;
		cabMetier.ajoutProfil(profil);
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		messageSucces = profil+" a été enregistré";
		return "redirect:/profil?messageSucces="+messageSucces;
	}
	
	@RequestMapping("/supprimerProfil")
	public String supprimerProfil(@ModelAttribute("unProfil") Profil unProfil, Model model, Long id) {
		String profilASupprimer = cabMetier.findByIdProfil(id).get().getNomProfil();
		String messageSucces = null;
		cabMetier.supprimerProfil(id);
		List<Profil> listProfil=cabMetier.findAllProfil();
		model.addAttribute("listProfil", listProfil);
		messageSucces = profilASupprimer+" a été supprimé";
		return "redirect:/profil?messageSucces="+messageSucces;
	}
	
}
