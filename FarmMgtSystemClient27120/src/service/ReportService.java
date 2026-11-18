/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author PC
 */
import dto.ReportDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReportService extends Remote {
    List<ReportDTO> generateUserReport(String nationalId) throws RemoteException;
    byte[] exportToPdf(List<ReportDTO> reportData) throws RemoteException;
    byte[] exportToExcel(List<ReportDTO> reportData) throws RemoteException;
    byte[] exportToCsv(List<ReportDTO> reportData) throws RemoteException;
}
