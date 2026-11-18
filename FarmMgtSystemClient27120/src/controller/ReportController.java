/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.ReportDTO;
import java.rmi.Naming;
import java.util.List;
import util.SessionManager;

/**
 *
 * @author PC
 */
public class ReportController {
    private static ReportController instance;
    private service.ReportService reportService;
    
    private ReportController() {
        try {
            // Lookup the RMI service using the correct port
            reportService = (service.ReportService) Naming.lookup("rmi://localhost:3001/ReportService");
        } catch (java.rmi.ConnectException e) {
            System.err.println("Connection error: " + e.getMessage());
            reportService = null;
        } catch (java.rmi.NotBoundException e) {
            System.err.println("Report service not available on server: " + e.getMessage());
            reportService = null;
        } catch (Exception e) {
            System.err.println("Error connecting to ReportService: " + e.getMessage());
            reportService = null;
        }
    }
    
    public static synchronized ReportController getInstance() {
        if (instance == null) {
            instance = new ReportController();
        }
        return instance;
    }
    
    public List<ReportDTO> generateUserReport() throws Exception {
        if (reportService == null) {
            throw new Exception("Unable to connect to report service. Please ensure the server is running and try again.");
        }
        
        SessionManager session = SessionManager.getInstance();
        if (!session.isLoggedIn()) {
            throw new Exception("You must be logged in to view reports. Please log in and try again.");
        }
        
        try {
            String nationalId = session.getCurrentUserId();
            return reportService.generateUserReport(nationalId);
        } catch (java.rmi.RemoteException e) {
            throw new Exception("Error generating report: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error while generating report: " + e.getMessage());
        }
    }
    
    public byte[] exportToPdf(List<ReportDTO> reportData) throws Exception {
        if (reportService == null) {
            throw new Exception("Unable to connect to report service. Please ensure the server is running and try again.");
        }
        try {
            byte[] result = reportService.exportToPdf(reportData);
            if (result == null) {
                throw new Exception("Failed to generate PDF report. Please try again.");
            }
            return result;
        } catch (java.rmi.RemoteException e) {
            throw new Exception("Error generating PDF report: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error while generating PDF: " + e.getMessage());
        }
    }
    
    public byte[] exportToExcel(List<ReportDTO> reportData) throws Exception {
        if (reportService == null) {
            throw new Exception("Unable to connect to report service. Please ensure the server is running and try again.");
        }
        try {
            byte[] result = reportService.exportToExcel(reportData);
            if (result == null) {
                throw new Exception("Failed to generate Excel report. Please try again.");
            }
            return result;
        } catch (java.rmi.RemoteException e) {
            throw new Exception("Error generating Excel report: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error while generating Excel: " + e.getMessage());
        }
    }
    
    public byte[] exportToCsv(List<ReportDTO> reportData) throws Exception {
        if (reportService == null) {
            throw new Exception("Unable to connect to report service. Please ensure the server is running and try again.");
        }
        try {
            byte[] result = reportService.exportToCsv(reportData);
            if (result == null) {
                throw new Exception("Failed to generate CSV report. Please try again.");
            }
            return result;
        } catch (java.rmi.RemoteException e) {
            throw new Exception("Error generating CSV report: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error while generating CSV: " + e.getMessage());
        }
    }
}
