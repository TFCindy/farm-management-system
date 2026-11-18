/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.implementation;

/**
 *
 * @author PC
 */
import dao.ReportDao;
import dto.ReportDTO;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ReportService;
import util.PDFExporter;
import util.ExcelExporter;
import util.CSVExporter;

public class ReportServiceImpl extends UnicastRemoteObject implements ReportService {
    private final ReportDao reportDao;
    
    public ReportServiceImpl() throws RemoteException {
        super();
        this.reportDao = new ReportDao();
    }

    @Override
    public List<ReportDTO> generateUserReport(String nationalId) throws RemoteException {
        return reportDao.getUserReportData(nationalId);
    }

    @Override
    public byte[] exportToPdf(List<ReportDTO> reportData) throws RemoteException {
        try {
            return PDFExporter.exportToPdf(reportData);
        } catch (IOException ex) {
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public byte[] exportToExcel(List<ReportDTO> reportData) throws RemoteException {
        try {
            return ExcelExporter.exportToExcel(reportData);
        } catch (IOException ex) {
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public byte[] exportToCsv(List<ReportDTO> reportData) throws RemoteException {
        try {
            return CSVExporter.exportToCsv(reportData);
        } catch (IOException ex) {
            Logger.getLogger(ReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
