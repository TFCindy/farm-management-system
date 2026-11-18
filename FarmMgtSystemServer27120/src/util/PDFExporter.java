/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author PC
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dto.ReportDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFExporter {
    public static byte[] exportToPdf(List<ReportDTO> reportData) throws IOException {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            // Add title
            Paragraph title = new Paragraph("Farm Management System Report", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Create table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 2, 2, 2});
            
            // Add table headers
            String[] headers = {"Item Name", "Type", "Date", "Quantity", "Status"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor(new com.itextpdf.text.BaseColor(51, 122, 183));
                cell.setBorderWidth(1);
                cell.setPhrase(new Phrase(header, 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);
            }
            
            // Add table rows
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (ReportDTO item : reportData) {
                table.addCell(new Phrase(item.getItemName(), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
                table.addCell(new Phrase(item.getItemType(), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
                table.addCell(new Phrase(dateFormat.format(item.getDate()), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
                table.addCell(new Phrase(String.valueOf(item.getQuantity()), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
                table.addCell(new Phrase(item.getStatus(), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            }
            
            document.add(table);
            document.close();
            
            return out.toByteArray();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
   }
}