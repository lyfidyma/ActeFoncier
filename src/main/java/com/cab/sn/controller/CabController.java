package com.cab.sn.controller;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cab.sn.dao.DocumentsRepository;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.metier.ICabMetier;

@Controller
public class CabController {
	
	@Autowired
	private ICabMetier cabMetier;
	@Autowired
	private DocumentsRepository documentsRepository;
	
	@RequestMapping("/doc")
	public String index(Model model, @ModelAttribute("unDocument") Documents documents) {
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
	public String sauvegarder(Model model, Long idDocument, String numDocument, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date dateDocument,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date dateCreation, String titreGlobal,String objetDocument, String statutDocument, 
			String typeBeneficiaire,String responsableDocument, int lot, String nicad, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date dateApprobation, int superficie, String nomEntreprise, String ninea, 
			Long cni, String nomPersonne, String prenom, Long nin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date dateDelivrance, 
			String commune, String communeArrond, String departement, String region, String typeDoc, 
			String numPj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date datePj, String objetPj ) {
		
			Documents doc = cabMetier.sauvegarderDocuments(idDocument, numDocument, dateDocument,	dateCreation, titreGlobal, 
					objetDocument, statutDocument, typeBeneficiaire, responsableDocument, lot, nicad, dateApprobation, 
					superficie, nomEntreprise, ninea, cni, nomPersonne, prenom, nin, dateDelivrance, commune, communeArrond,
					departement, region, typeDoc, numPj, datePj, objetPj);
			
			model.addAttribute("unDocument", doc);
			
		return "redirect:/doc";
		
	}
	
	@RequestMapping(value="/supprimer", method=RequestMethod.GET)
	public String supprimer(Long id, int page, int size, String motCle) {
		cabMetier.supprimerDocuments(id);
		return "redirect:/cons?page="+page+"&size="+size+"&motCle="+motCle+"";
	}
	
	@RequestMapping(value="/modifier", method=RequestMethod.GET)
	public String modifier(Model model, Long id) {
		Documents doc = cabMetier.modifierDocuments(id);
		model.addAttribute("unDocument",doc);
		return "formulaire";
	}
	
	@RequestMapping(value="/visualiser", method=RequestMethod.GET)
	public String visualiser(Model model, Long id) {
		Documents doc = cabMetier.visualiserDocuments(id);
		//Collection <PiecesJointes> pj= doc.getPiecesJointes();
		model.addAttribute("document", doc);
		return "transmission";
	}
	
	@RequestMapping(value="/transmettre", method=RequestMethod.POST)
	public String transmission(Model model, Long idDocument) {
			cabMetier.transmettreDocuments(idDocument);
		return "redirect:/cons";
	}

}
