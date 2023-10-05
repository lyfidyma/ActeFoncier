package com.cab.sn.view;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cab.sn.entities.Documents;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DocumentsExcelExport extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		response.addHeader("Content-Disposition", "attachment; filename=DocumentsExport.xlsx");
		@SuppressWarnings("unchecked")
		List <Documents> list = (List<Documents>) model.get("list");
		Sheet sheet = workbook.createSheet("Documents");
		CreationHelper createHelper = workbook.getCreationHelper();
		short format = createHelper.createDataFormat().getFormat("dd-mm-yyyy");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format);
			
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("Numéro d'ordre");
		row0.createCell(1).setCellValue("Type de document");
		row0.createCell(2).setCellValue("Date document");
		row0.createCell(3).setCellValue("Numéro document");
		row0.createCell(4).setCellValue("Objet");
		row0.createCell(5).setCellValue("Date approbation");
		row0.createCell(6).setCellValue("Site");
		row0.createCell(7).setCellValue("Lot");
		row0.createCell(8).setCellValue("Titre global");
		row0.createCell(9).setCellValue("Superficie");
		row0.createCell(10).setCellValue("Nicad");
		row0.createCell(11).setCellValue("Bénéficiaire");
		row0.createCell(12).setCellValue("Statut");
		
		int rowNum = 1;
		for(Documents documents:list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(list.indexOf(documents)+1);
			row.createCell(1).setCellValue(documents.getTypeDocument().getTypeDoc());
			Cell cell = row.createCell(2);
			cell.setCellValue(documents.getDateDocument());
			cell.setCellStyle(cellStyle);
			row.createCell(3).setCellValue(documents.getNumDocument());
			row.createCell(4).setCellValue(documents.getObjetDocument());
			Cell cell1 = row.createCell(5);
			cell1.setCellValue(documents.getDateApprobation());
			cell1.setCellStyle(cellStyle);
			row.createCell(6).setCellValue(documents.getLocalisation().getCommune());
			row.createCell(7).setCellValue(documents.getLot());
			row.createCell(8).setCellValue(documents.getTitreGlobal());
			row.createCell(9).setCellValue(documents.getSuperficie());
			row.createCell(10).setCellValue(documents.getNicad());
			row.createCell(11).setCellValue(documents.getBeneficiaire().getIdBeneficiaire());
			row.createCell(12).setCellValue(documents.getStatutDocument());
		}
		
		
	}

}
