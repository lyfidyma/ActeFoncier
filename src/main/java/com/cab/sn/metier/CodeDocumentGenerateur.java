package com.cab.sn.metier;

import java.util.Date;
import java.util.UUID;

public class CodeDocumentGenerateur {
	public static String genererCodeDocument(Date dateCreation, String typeDoc, String commune) {
		return dateCreation.toString().substring(32, 34).concat(typeDoc.toUpperCase()).
				concat(commune.substring(0, 2)).toUpperCase().concat(UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(27, 32));
	}

}
