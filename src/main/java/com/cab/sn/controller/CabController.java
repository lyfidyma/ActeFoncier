package com.cab.sn.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.metier.ICabMetier;

import jakarta.validation.Valid;


@Controller
public class CabController {
	
	@Autowired
	private ICabMetier cabMetier;

	@RequestMapping(value={"/", "/index"})
	public String index() {		
		return "index";
	}
	@RequestMapping("/ajouter")
	public String ajouter(Model model, @ModelAttribute("unDocument") Documents documents) {
			model.addAttribute("unDocument",documents);			
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
	public String sauvegarder(@Valid @ModelAttribute("unDocument") Documents doc, BindingResult result, Model model, Long idDocument, String numDocument, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  dateDocument,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreation, 
			String titreGlobal,String objetDocument, String statutDocument, 
			String typeBeneficiaire,String responsableDocument, String lot, String nicad, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateApprobation, String superficie, String nomEntreprise, String ninea, 
			Long cni, String nomPersonne, String prenom, Long nin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDelivrance, 
			String commune, String communeArrond, String departement, String region, String typeDoc, 
			String numPj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj, String objetPj,
			String numPj1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj1, String objetPj1, @RequestParam(name="flag",defaultValue="") String  flag) {
		
		  if (result.hasErrors()) {
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
		  String messageDoublon ="";
		  String message = "";
		  String messageTypeDoc = "";
		  String regex = "^[1-2]\\d{12}$";
		  Pattern pattern = Pattern.compile(regex);
		 if(cni==null) {
			 message = "Renseigner la cni";
			 model.addAttribute("message", message);

			 if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		 }
		 Matcher matcher = pattern.matcher(cni.toString());
		 if (matcher.find()==false) {
			 message = "Renseigner la cni au bon format";
			 model.addAttribute("message", message);
			 if(idDocument == null)
				  	return "formulaire"; 
			  return "modifier"; 
		 }
		 if(typeDoc=="")
		 {
			 messageTypeDoc ="Choisir un type";
			 model.addAttribute("messageTypeDoc", messageTypeDoc);
			 if(idDocument== null)
				  	return "formulaire"; 
			  return "modifier";
		 }
		 
		 List <Documents> controleDocument = cabMetier.findByCni(cni);
		 
		if(cabMetier.findByCni(cni)!=null) {
			for(Documents document:controleDocument)
			{
			if(document.getTypeDocument().getTypeDoc().equals(typeDoc) && 
				document.getNumDocument().equals(numDocument)	){
				if(document.getTypeBeneficiaire().equals("Particulier")){
					if(cabMetier.chercherPersonneParId(document.getBeneficiaire().getIdBeneficiaire()).getCni().equals(cni)) {
						messageDoublon="Ce document existe sous le numéro "+document.getCodeUniqueDocument();
						model.addAttribute("messageDoublon", messageDoublon);
						return "formulaire";
					}
				
				}else if (document.getTypeBeneficiaire().equals("Entreprise")) {
					if(cabMetier.chercherEntreprise(document.getBeneficiaire().getIdBeneficiaire()).getNinea().equals(ninea)){
						if(cabMetier.chercherEntreprise(document.getBeneficiaire().getIdBeneficiaire()).getPersonne().getCni().equals(cni)) {
							messageDoublon="Ce document existe sous le numéro "+document.getCodeUniqueDocument();
							model.addAttribute("messageDoublon", messageDoublon);
							return "formulaire";
					}
				}
			}
			
		}
	}
}
		
		
			doc = cabMetier.sauvegarderDocuments(idDocument, numDocument, dateDocument, titreGlobal, 
					objetDocument, statutDocument, typeBeneficiaire, responsableDocument, lot, nicad, dateApprobation, 
					superficie, nomEntreprise, ninea, cni, nomPersonne, prenom, nin, dateDelivrance, commune, communeArrond,
					departement, region, typeDoc, numPj, datePj, objetPj, numPj1, datePj1, objetPj1);
			
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
	public String pageValidation() {		
		return "validation";
	}
	
	@RequestMapping(value="/valider", method=RequestMethod.POST)
	public String valider(Model model, Long idDocument, String codeUniqueDocument, boolean checkboxValider, boolean checkboxRejeter) {
		String messageValiderOuRejeter="";
		if((checkboxValider && checkboxRejeter) || (checkboxValider==false && checkboxRejeter==false)) {
			messageValiderOuRejeter = "Approuvez ou rejetez le document";
			//model.addAttribute("messageValiderOuRejeter", messageValiderOuRejeter);
			return "redirect:/afficherDonneesValidation?codeUniqueDocument="+codeUniqueDocument+"&messageValiderOuRejeter="+messageValiderOuRejeter+"";
		}
			cabMetier.validerDocuments(idDocument, checkboxValider, checkboxRejeter);
		return "redirect:/cons";
	}
}
