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
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVExporter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static byte[] exportToCsv(List<ReportDTO> reportData) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                 "Item Name", "Type", "Date", "Quantity", "Status"))) {
            
            // Write data
            for (ReportDTO item : reportData) {
                printer.printRecord(
                    item.getItemName(),
                    item.getItemType(),
                    DATE_FORMAT.format(item.getDate()),
                    item.getQuantity(),
                    item.getStatus()
                );
            }
            
            printer.flush();
            return out.toByteArray();
        }
    }
}
