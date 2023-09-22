package com.cab.sn.controller;

import java.time.LocalDate;
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
			String typeBeneficiaire,String responsableDocument, int lot, String nicad, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateApprobation, int superficie, String nomEntreprise, String ninea, 
			Long cni, String nomPersonne, String prenom, Long nin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDelivrance, 
			String commune, String communeArrond, String departement, String region, String typeDoc, 
			String numPj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj, String objetPj,
			String numPj1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePj1, String objetPj1) {
		
		  if (result.hasErrors()) {
			  if(idDocument==null)
				  	return "formulaire"; 
			  return "modifier";
		  }
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
			doc = cabMetier.sauvegarderDocuments(idDocument, numDocument, dateDocument,	dateCreation, titreGlobal, 
					objetDocument, statutDocument, typeBeneficiaire, responsableDocument, lot, nicad, dateApprobation, 
					superficie, nomEntreprise, ninea, cni, nomPersonne, prenom, nin, dateDelivrance, commune, communeArrond,
					departement, region, typeDoc, numPj, datePj, objetPj, numPj1, datePj1, objetPj1);
			
			
			model.addAttribute("unDocument", doc);
			
			if(idDocument== null)
			  	return "formulaire"; 
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
		if(cabMetier.modifierDocuments(id).getDateApprobation()!=null)
			 dateApprobationString =cabMetier.modifierDocuments(id).getDateApprobation().toString();
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

}
