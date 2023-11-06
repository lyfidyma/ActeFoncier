package com.cab.sn.metier;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CodeDocumentGenerateur{
	
	public static String genererCodeDocument(Date dateCreation, String typeDoc, String commune) {
		
		Long numCodeStart=1000L;
		Long numCodeEnd = 9999L;
		//Long numCode = new Random().nextLong();
		Long numCode = numCodeStart + (long) (Math.random()*(numCodeEnd-numCodeStart));
		
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		String anneeString = Integer.toString(annee);
		
		// Extrait les 2 derniers caractères de la représentation de l'année 
	    String datePart = (anneeString != null && anneeString.length() == 4) ?
	        anneeString.substring(2, 4) : "";
	   
	    // Convertit le type de document en majuscules
	    String typeDocPart = (typeDoc != null) && typeDoc.length() >= 2 ? 
	    		typeDoc.substring(0, 2).toUpperCase() : "";
	    
	    // Extrait les 2 premiers caractères du nom de la commune
	    String communePart = (commune != null && commune.length() >= 2) ?
	        commune.substring(0, 2).toUpperCase() : "";
	    
	    // Génère une chaîne UUID, supprime les tirets et extrait les caractères de la position 27 à 31
	    String uuidPart = UUID.randomUUID().toString().toUpperCase().replace("-", "");
	    uuidPart = (uuidPart.length() >= 32) ? uuidPart.substring(27, 32) : "";
	    
	    // Concatène les différentes parties pour former le code du document
	    return datePart.concat(typeDocPart).concat(communePart).concat(numCode.toString());
	}

}
