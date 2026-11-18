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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import model.EmployeeContract;

public interface EmployeeContractService extends Remote {
    
    // CRUD Operations
    public EmployeeContract saveContract(EmployeeContract theContract) throws RemoteException;
    public EmployeeContract updateContract(EmployeeContract theContract) throws RemoteException;
    public EmployeeContract deleteContract(EmployeeContract theContract) throws RemoteException;
    public EmployeeContract searchContractById(EmployeeContract theContract) throws RemoteException;
    public List<EmployeeContract> retrieveAllContracts() throws RemoteException;
    
    // Find operations
    public EmployeeContract findById(int contractId) throws RemoteException;
    public EmployeeContract findByEmployeeId(int employeeId) throws RemoteException;
    public List<EmployeeContract> findByContractType(String contractType) throws RemoteException;
    
    // Special queries
    public List<EmployeeContract> findActiveContracts() throws RemoteException;
    public List<EmployeeContract> findExpiredContracts() throws RemoteException;
    public List<EmployeeContract> findContractsByUser(String nationalId) throws RemoteException;
    public List<EmployeeContract> getContractsByNationalId(String nationalId) throws RemoteException;

    
    // Count operations
    public Long countAllContracts() throws RemoteException;
    public Long countActiveContractsByUser(String nationalId) throws RemoteException;
    
    // Validation
    public boolean contractExistsForEmployee(int employeeId) throws RemoteException;
    
    // Update specific fields
    public boolean updateContractEndDate(int contractId, Date endDate) throws RemoteException;
    
    // Contract status
    public boolean isContractActive(int contractId) throws RemoteException;
}