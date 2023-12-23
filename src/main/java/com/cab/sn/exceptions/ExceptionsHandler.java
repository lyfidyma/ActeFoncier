package com.cab.sn.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
	 
}
