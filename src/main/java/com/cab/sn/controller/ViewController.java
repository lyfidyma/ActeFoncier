package com.cab.sn.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cab.sn.entities.Documents;
import com.cab.sn.metier.CabMetierImpl;
import com.cab.sn.view.DocumentsExcelExport;
import com.cab.sn.view.DocumentsPdfExport;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ViewController {
	
	@Autowired
	private CabMetierImpl cabMetierImpl;
	
	@GetMapping("/excelExport")
	public ModelAndView exportVersExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new DocumentsExcelExport());
		List<Documents> list = cabMetierImpl.tousLesDocuments();
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
	         
	        Documents doc = cabMetierImpl.visualiserDocuments(id);
	         
	        DocumentsPdfExport exporter = new DocumentsPdfExport(doc);
	        exporter.export(response);
	         
	    }

}
