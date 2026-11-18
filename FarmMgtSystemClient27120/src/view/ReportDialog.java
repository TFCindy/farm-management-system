/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author PC
 */
import controller.ReportController;
import dto.ReportDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import util.SessionManager;
import util.TranslationManager;
/**
 *
 * @author PC
 */
public class ReportDialog extends BaseDialog {
    private ReportController reportController;
    private List<ReportDTO> reportData;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // UI Components
    private JScrollPane scrollPane;
    private JTable tblReport;
    private JPanel buttonPanel;
    private JButton btnPrint;
    private JButton btnExportPdf;
    private JButton btnExportExcel;
    private JButton btnExportCsv;
    private JButton btnClose;
    private JLabel titleLabel;
    
    public ReportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        buildUI();
        loadReportData();
        setLocationRelativeTo(parent);
    }
    
    private void buildUI() {
        // Remove NetBeans generated layout
        getContentPane().removeAll();
        
        setTitle(TranslationManager.getTranslation("Reports"));
        setSize(900, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Title
        titleLabel = new JLabel();
        applyLabelTranslation(titleLabel, "Reports");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 122, 183)); // Blue color
        titleLabel.setBorder(new EmptyBorder(15, 15, 10, 15));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create table
        createReportTable();
        
        // Scroll pane for table
        scrollPane = new JScrollPane(tblReport);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void createReportTable() {
        tblReport = new JTable();
        tblReport.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Item Name", "Type", "Date", "Quantity", "Status"}
        ) {
            Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                false, false, false, false, false
            };
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // Style table header
        JTableHeader header = tblReport.getTableHeader();
        header.setBackground(new Color(51, 122, 183));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setReorderingAllowed(false);
        
        // Style table
        tblReport.setBackground(Color.WHITE);
        tblReport.setRowHeight(25);
        tblReport.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tblReport.setSelectionBackground(new Color(51, 122, 183));
        tblReport.setSelectionForeground(Color.WHITE);
        tblReport.setGridColor(new Color(220, 220, 220));
        
        // Set column widths
        tblReport.getColumnModel().getColumn(0).setPreferredWidth(200); // Item Name
        tblReport.getColumnModel().getColumn(1).setPreferredWidth(100); // Type
        tblReport.getColumnModel().getColumn(2).setPreferredWidth(120); // Date
        tblReport.getColumnModel().getColumn(3).setPreferredWidth(80);  // Quantity
        tblReport.getColumnModel().getColumn(4).setPreferredWidth(100); // Status
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Print button
        btnPrint = new JButton();
        applyButtonTranslation(btnPrint, "Print");
        btnPrint.setBackground(new Color(51, 122, 183));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnPrint.setPreferredSize(new Dimension(100, 35));
        btnPrint.addActionListener(this::btnPrintActionPerformed);
        panel.add(btnPrint);
        
        // Export PDF button
        btnExportPdf = new JButton();
        applyButtonTranslation(btnExportPdf, "Export PDF");
        btnExportPdf.setBackground(new Color(51, 122, 183));
        btnExportPdf.setForeground(Color.WHITE);
        btnExportPdf.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExportPdf.setPreferredSize(new Dimension(120, 35));
        btnExportPdf.addActionListener(this::btnExportPdfActionPerformed);
        panel.add(btnExportPdf);
        
        
        
        // Export CSV button
        btnExportCsv = new JButton();
        applyButtonTranslation(btnExportCsv, "Export CSV");
        btnExportCsv.setBackground(new Color(51, 122, 183));
        btnExportCsv.setForeground(Color.WHITE);
        btnExportCsv.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExportCsv.setPreferredSize(new Dimension(120, 35));
        btnExportCsv.addActionListener(this::btnExportCsvActionPerformed);
        panel.add(btnExportCsv);
        
        // Close button
        btnClose = new JButton();
        applyButtonTranslation(btnClose, "Close");
        btnClose.setBackground(new Color(217, 83, 79)); // Red color
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnClose.setPreferredSize(new Dimension(100, 35));
        btnClose.addActionListener(this::btnCloseActionPerformed);
        panel.add(btnClose);
        
        return panel;
    }
    
    private void loadReportData() {
        SwingUtilities.invokeLater(() -> {
            try {
                reportController = ReportController.getInstance();
                reportData = reportController.generateUserReport();
                
                if (reportData == null || reportData.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        TranslationManager.getTranslation("No_report_data_available"), 
                        TranslationManager.getTranslation("No_Data"), 
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    return;
                }
                
                populateTable();
                
                // Add auto-refresh timer to update report every 30 seconds
                javax.swing.Timer refreshTimer = new javax.swing.Timer(30000, e -> {
                    try {
                        List<ReportDTO> newData = reportController.generateUserReport();
                        if (newData != null && !newData.isEmpty()) {
                            reportData = newData;
                            populateTable();
                        }
                    } catch (Exception ex) {
                        // Silently handle refresh errors to avoid disrupting user
                        System.err.println("Error refreshing report: " + ex.getMessage());
                    }
                });
                refreshTimer.start();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    TranslationManager.getTranslation("Error_loading_report") + ": " + ex.getMessage(), 
                    TranslationManager.getTranslation("Error"), 
                    JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        });
    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        
        for (ReportDTO item : reportData) {
            Object[] rowData = new Object[]{
                item.getItemName(),
                item.getItemType(),
                dateFormat.format(item.getDate()),
                item.getQuantity(),
                item.getStatus()
            };
            model.addRow(rowData);
            
            // Apply special styling for health alerts (sick animals summary)
            if ("Health Alert".equals(item.getItemType())) {
                int rowIndex = model.getRowCount() - 1;
                // Make the row stand out with different styling
                tblReport.setRowHeight(rowIndex, 30); // Make the row taller
            }
        }
        
        // Auto-resize columns to fit content
        tblReport.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    
    private void exportToFile(byte[] data, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(TranslationManager.getTranslation("Save_Report"));
        fileChooser.setSelectedFile(new File("FarmReport_" + 
            new SimpleDateFormat("yyyyMMdd").format(new Date()) + extension));
        
        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter(
            extension.substring(1).toUpperCase() + " Files", 
            extension.substring(1));
        fileChooser.setFileFilter(filter);
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(extension)) {
                file = new File(file.getAbsolutePath() + extension);
            }
            
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
                JOptionPane.showMessageDialog(this, 
                    TranslationManager.getTranslation("Report_exported_successfully") + ":\n" + 
                    file.getAbsolutePath(), 
                    TranslationManager.getTranslation("Export_Successful"), 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    TranslationManager.getTranslation("Error_exporting_report") + ": " + ex.getMessage(), 
                    TranslationManager.getTranslation("Export_Error"), 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Event handlers
    private void btnPrintActionPerformed(ActionEvent evt) {                                         
        try {
            tblReport.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                TranslationManager.getTranslation("Error_printing_report") + ": " + ex.getMessage(), 
                TranslationManager.getTranslation("Print_Error"), 
                JOptionPane.ERROR_MESSAGE);
        }
    }                                        
    private void btnExportPdfActionPerformed(ActionEvent evt) {                                             
        try {
            byte[] pdfData = reportController.exportToPdf(reportData);
            exportToFile(pdfData, ".pdf");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                TranslationManager.getTranslation("Error_exporting_to_PDF") + ": " + ex.getMessage(), 
                TranslationManager.getTranslation("Export_Error"), 
                JOptionPane.ERROR_MESSAGE);
        }
    }                                            
    private void btnExportExcelActionPerformed(ActionEvent evt) {                                               
        try {
            byte[] excelData = reportController.exportToExcel(reportData);
            exportToFile(excelData, ".xlsx");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                TranslationManager.getTranslation("Error_exporting_to_Excel") + ": " + ex.getMessage(), 
                TranslationManager.getTranslation("Export_Error"), 
                JOptionPane.ERROR_MESSAGE);
        }
    }                                              
    private void btnExportCsvActionPerformed(ActionEvent evt) {                                             
        try {
            byte[] csvData = reportController.exportToCsv(reportData);
            exportToFile(csvData, ".csv");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                TranslationManager.getTranslation("Error_exporting_to_CSV") + ": " + ex.getMessage(), 
                TranslationManager.getTranslation("Export_Error"), 
                JOptionPane.ERROR_MESSAGE);
        }
    }                                            
    private void btnCloseActionPerformed(ActionEvent evt) {                                         
        dispose();
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReportDialog dialog = new ReportDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
