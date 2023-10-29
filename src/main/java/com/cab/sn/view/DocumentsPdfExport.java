package com.cab.sn.view;

import java.io.IOException;
import java.util.Date;

import com.cab.sn.entities.Documents;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class DocumentsPdfExport {
	
	private Documents documents;
	public DocumentsPdfExport(Documents documents) {
		this.documents = documents;
	}
	
	/*
	 * private void writeTableHeader(PdfPTable table) { PdfPCell cell = new
	 * PdfPCell();
	 * 
	 * cell.setBackgroundColor(Color.WHITE); cell.setPadding(5);
	 * 
	 * Font font = FontFactory.getFont(FontFactory.HELVETICA);
	 * font.setColor(Color.WHITE); //cell.setPhrase(new Phrase("Numéro d'orde",
	 * font)); //table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Type document\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Numero document\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Date document\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Objet\n", font)); table.addCell(cell);
	 * 
	 * //cell.setPhrase(new Phrase("Date approbation", font));
	 * //table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Bénéficiaire\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Site\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Lot\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Titre global\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Superficie\n", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Nicad\n", font)); table.addCell(cell);
	 * 
	 * //cell.setPhrase(new Phrase("Statut", font)); //table.addCell(cell); }
	 */
	
	private void writeTableData(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		font.setSize(10);
		Font font1 = FontFactory.getFont(FontFactory.COURIER);
		font1.setSize(10);
		//font.setColor(Color.WHITE);
		
		PdfPCell cell1= new PdfPCell();
		  cell1.setPhrase(new Phrase("Type de document", font));
		  cell1.setBorder(0);
		  cell1.setPadding(9);
		  table.addCell(cell1);
		  //table.addCell(documents.getTypeDocument().getTypeDoc());		  
		  PdfPCell cell2= new PdfPCell();
		  cell2.setPhrase(new Phrase(documents.getTypeDocument().getTypeDoc(), font1));
		  cell2.setBorder(0);
		  cell2.setPadding(9);
		  table.addCell(cell2);
		  //table.addCell(documents.getTypeDocument().getTypeDoc());	
		  
		  PdfPCell cell3= new PdfPCell();
		  cell3.setPhrase(new Phrase("Numéro du document", font));
		  cell3.setBorder(0);
		  cell3.setPadding(9);
		  table.addCell(cell3);
		  PdfPCell cell4= new PdfPCell();
		  cell4.setPhrase(new Phrase(documents.getNumDocument(), font1));
		  cell4.setBorder(0);
		  cell4.setPadding(9);
		  table.addCell(cell4);
		  
		  PdfPCell cell5= new PdfPCell();
		  cell5.setPhrase(new Phrase("Date du document", font));
		  cell5.setBorder(0);
		  cell5.setPadding(9);
		  table.addCell(cell5);
		  PdfPCell cell6= new PdfPCell();
		  cell6.setPhrase(new Phrase(String.valueOf(documents.getDateDocument()), font1));
		  cell6.setBorder(0);
		  cell6.setPadding(9);
		  table.addCell(cell6);
		  
		  PdfPCell cell7= new PdfPCell();
		  cell7.setPhrase(new Phrase("Objet", font));
		  cell7.setBorder(0);
		  cell7.setPadding(9);
		  table.addCell(cell7);
		  PdfPCell cell8= new PdfPCell();
		  cell8.setPhrase(new Phrase(documents.getObjetDocument(), font1));
		  cell8.setBorder(0);
		  cell8.setPadding(9);
		  table.addCell(cell8);
		  
		  
		  PdfPCell cell9= new PdfPCell();
		  cell9.setPhrase(new Phrase("Bénéficiaire", font));
		  cell9.setBorder(0);
		  cell9.setPadding(9);
		  table.addCell(cell9);
		  PdfPCell cell10= new PdfPCell();
		  if(documents.getPersonne()!=null && documents.getEntreprise() == null)
			  cell10.setPhrase(new Phrase(String.valueOf(documents.getPersonne().getCni()), font1));
		  if(documents.getEntreprise()!=null)
			  cell10.setPhrase(new Phrase(String.valueOf(documents.getEntreprise().getNomEntreprise()), font1));
		  cell10.setBorder(0);
		  cell10.setPadding(9);
		  table.addCell(cell10);

		  PdfPCell cell11 = new PdfPCell();
		  cell11.setPhrase(new Phrase("Site", font));
		  cell11.setBorder(0);
		  cell11.setPadding(9);
		  table.addCell(cell11);
		  PdfPCell cell12 = new PdfPCell();
		  cell12.setPhrase(new Phrase(documents.getCommune().getLibelleCommune(), font1));
		  cell12.setBorder(0);
		  cell12.setPadding(9);
		  table.addCell(cell12);
			
		  PdfPCell cell13= new PdfPCell();
		  cell13.setPhrase(new Phrase("Lot", font));
		  cell13.setBorder(0);
		  cell13.setPadding(9);
		  table.addCell(cell13);
		  PdfPCell cell14= new PdfPCell();
		  cell14.setPhrase(new Phrase(String.valueOf(documents.getLot()), font1));
		  cell14.setBorder(0);
		  cell14.setPadding(9);
		  table.addCell(cell14);
		  
		  PdfPCell cell15= new PdfPCell();
		  cell15.setPhrase(new Phrase("Titre global", font));
		  cell15.setBorder(0);
		  cell15.setPadding(9);
		  table.addCell(cell15);
		  PdfPCell cell16= new PdfPCell();
		  cell16.setPhrase(new Phrase(String.valueOf(documents.getTitreGlobal()), font1));
		  cell16.setBorder(0);
		  cell16.setPadding(9);
		  table.addCell(cell16);
		
		  PdfPCell cell17= new PdfPCell();
		  cell17.setPhrase(new Phrase("Superficie", font));
		  cell17.setBorder(0);
		  cell17.setPadding(9);
		  table.addCell(cell17);
		  PdfPCell cell18= new PdfPCell();
		  cell18.setPhrase(new Phrase(String.valueOf(documents.getSuperficie()), font1));
		  cell18.setBorder(0);
		  cell18.setPadding(9);
		  table.addCell(cell18);
		
		  PdfPCell cell19= new PdfPCell();
		  cell19.setPhrase(new Phrase("Nicad", font));
		  cell19.setBorder(0);
		  cell19.setPadding(9);
		  table.addCell(cell19);
		  PdfPCell cell20= new PdfPCell();
		  cell20.setPhrase(new Phrase(String.valueOf(documents.getNicad()), font1));
		  cell20.setBorder(0);
		  cell20.setPadding(9);
		  table.addCell(cell20);
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		//document.setPageSize(PageSize.A4.rotate());
		PdfWriter.getInstance(document, response.getOutputStream());
		Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
        fontFooter.setSize(7);
  
		HeaderFooter headFooter = new HeaderFooter(new Phrase("Imprimé le "+new Date(), fontFooter), false);
		document.setFooter(headFooter);
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
        font.setSize(12);
        
		
		  Paragraph p = new Paragraph("Acte n° "+documents.getCodeUniqueDocument(), font);
		  p.setAlignment(Paragraph.ALIGN_CENTER);
	    
		
		  PdfPTable table = new PdfPTable(2); 
		  table.setWidthPercentage(80f);
		  table.setWidths(new float[] {3f, 3.5f}); 
		  table.setSpacingBefore(5);
		  table.setHorizontalAlignment(50);
		 
		 // writeTableHeader(table); 
		  writeTableData(table);
		  document.add(p);
		  document.add(table);
         
        document.close();
         
	} 

}
