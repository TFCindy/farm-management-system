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

import dto.ReportDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
    public static byte[] exportToExcel(List<ReportDTO> reportData) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            // Create a Sheet
            Sheet sheet = workbook.createSheet("Farm Report");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Item Name", "Type", "Date", "Quantity", "Status"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
cell.setCellValue(columns[i]);

// Create header style
CellStyle headerStyle = workbook.createCellStyle();
headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
headerStyle.setAlignment(HorizontalAlignment.CENTER);

// Create font
Font headerFont = workbook.createFont();
headerFont.setBold(true);
headerFont.setColor(IndexedColors.WHITE.getIndex());

headerStyle.setFont(headerFont);
cell.setCellStyle(headerStyle);
            }
            
            // Create data rows
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int rowNum = 1;
            for (ReportDTO item : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getItemName());
                row.createCell(1).setCellValue(item.getItemType());
                row.createCell(2).setCellValue(dateFormat.format(item.getDate()));
                row.createCell(3).setCellValue(item.getQuantity());
                row.createCell(4).setCellValue(item.getStatus());
            }
            
            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        } finally {
            workbook.close();
        }
    }
}