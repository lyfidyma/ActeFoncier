package com.cab.sn.metier;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CodeDocumentGenerateur {
	public static String genererCodeDocument(Date dateCreation, String typeDoc, String commune) {
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		String anneeString = Integer.toString(annee);
		return anneeString.substring(2, 4).concat(typeDoc.toUpperCase()).
				concat(commune.substring(0, 2)).toUpperCase().concat(UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(27, 32));
	}

}
