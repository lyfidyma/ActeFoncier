package com.cab.sn.exceptions;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cab.sn.entities.Commune;
import com.cab.sn.entities.CommuneArrondissement;
import com.cab.sn.entities.Departement;
import com.cab.sn.entities.Documents;
import com.cab.sn.entities.Entreprise;
import com.cab.sn.entities.Personne;
import com.cab.sn.entities.PiecesJointes;
import com.cab.sn.entities.Region;
import com.cab.sn.entities.TypeDocument;
import com.cab.sn.metier.ICabMetier;

import jakarta.validation.Valid;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionsHandler {
	
	
	  @ExceptionHandler(MaxUploadSizeExceededException.class) 
	  public String handleFileUploadError(RedirectAttributes ra) {
		  ra.addFlashAttribute("messageFile",  "La taille du fichier ne doit pas dépasser 10MB"); 
		  return "redirect:/ajouter";
	  
	  }
	 
	  
	  @ExceptionHandler(Exception.class) 
	  public String handleException(RedirectAttributes ra) {
	  return "redirect:/"; 
		
	  }
	 
	
		/*
		 * @ExceptionHandler(MaxUploadSizeExceededException.class) public ModelAndView
		 * handleMaxSizeException( MaxUploadSizeExceededException exc,
		 * HttpServletRequest request, HttpServletResponse response) {
		 * 
		 * ModelAndView modelAndView = new ModelAndView();
		 * modelAndView.setViewName("formulaire");
		 * modelAndView.getModel().put("messageFile",
		 * "La taille du fichier ne doit pas excéder 10MB"); return modelAndView; }
		 */
}
