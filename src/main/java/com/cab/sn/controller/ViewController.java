package com.cab.sn.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cab.sn.entities.Documents;
import com.cab.sn.metier.ICabMetier;
import com.cab.sn.view.DocumentsExcelExport;
import com.cab.sn.view.DocumentsPdfExport;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ViewController {
	
	@Autowired
	private ICabMetier cabMetier;
	
	@GetMapping("/excelExport")
	public ModelAndView exportVersExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new DocumentsExcelExport());
		List<Documents> list = cabMetier.tousLesDocuments();
		mav.addObject("list", list);
		return mav;
		
	}
	
	 @GetMapping("/pdfExport")
	    public void exportToPDF(HttpServletResponse response, Long id) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=documents_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        Documents doc = cabMetier.visualiserDocuments(id);
	         
	        DocumentsPdfExport exporter = new DocumentsPdfExport(doc);
	        exporter.export(response);
	         
	    }
	 @GetMapping("/resultatsExcelExport")
		public ModelAndView exportResultatsRechercheVersExcel(String typeDocument, String numDocument, String codeDocument, 
				@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDocument, String responsableDocument,
				String numCEDEAO, String cni, String ninea, String commune, String titreGlobal, String nicad) {
		 List<Documents> list = new ArrayList<>();
		 ModelAndView mav = new ModelAndView();
			mav.setView(new DocumentsExcelExport());
			if(typeDocument.isBlank()==false)
				list = cabMetier.chercherDocumentParCriteresPourExport(typeDocument, numDocument, codeDocument, dateDocument, 
					responsableDocument);
			if(cni.isBlank() == false || numCEDEAO.isBlank() == false || ninea.isBlank() == false)
				list = cabMetier.chercherDocumentParBeneficiairePourExport(numCEDEAO, cni, ninea);
			if(commune.isBlank()==false)
				list = cabMetier.chercherDocumentParCommunePourExport(commune);
			if(titreGlobal.isBlank()==false)
				list = cabMetier.chercherDocumentParTitreDeProprietePourExport(titreGlobal, nicad);
			
			mav.addObject("list", list);
			return mav;
			
		}

}
