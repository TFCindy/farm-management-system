package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import model.EmployeeContract;
import service.EmployeeContractService;

/**
 * Controller for EmployeeContract-related operations
 * Handles communication between View and RMI Service
 */
public class EmployeeContractController {
    
    private EmployeeContractService contractService;
    
    public EmployeeContractController() throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        this.contractService = (EmployeeContractService) registry.lookup("EmployeeContractService");
    }
    
    public EmployeeContract saveContract(EmployeeContract contract) throws RemoteException {
        return contractService.saveContract(contract);
    }
    
    public EmployeeContract updateContract(EmployeeContract contract) throws RemoteException {
        return contractService.updateContract(contract);
    }
    
    public EmployeeContract deleteContract(EmployeeContract contract) throws RemoteException {
        return contractService.deleteContract(contract);
    }
    
    public EmployeeContract searchContractById(EmployeeContract contract) throws RemoteException {
        return contractService.searchContractById(contract);
    }
    
    public List<EmployeeContract> retrieveAllContracts() throws RemoteException {
        return contractService.retrieveAllContracts();
    }
    
    public EmployeeContract findById(int contractId) throws RemoteException {
        return contractService.findById(contractId);
    }
    
    public EmployeeContract findByEmployeeId(int employeeId) throws RemoteException {
        return contractService.findByEmployeeId(employeeId);
    }
    
    public List<EmployeeContract> findByContractType(String contractType) throws RemoteException {
        return contractService.findByContractType(contractType);
    }
    
    public List<EmployeeContract> findActiveContracts() throws RemoteException {
        return contractService.findActiveContracts();
    }
    
    public List<EmployeeContract> findExpiredContracts() throws RemoteException {
        return contractService.findExpiredContracts();
    }
    
    public List<EmployeeContract> findContractsByUser(String nationalId) throws RemoteException {
        return contractService.findContractsByUser(nationalId);
    }
    
    public Long countAllContracts() throws RemoteException {
        return contractService.countAllContracts();
    }
    
    public Long countActiveContractsByUser(String nationalId) throws RemoteException {
        return contractService.countActiveContractsByUser(nationalId);
    }
    
    public boolean contractExistsForEmployee(int employeeId) throws RemoteException {
        return contractService.contractExistsForEmployee(employeeId);
    }
    
    public boolean updateContractEndDate(int contractId, Date endDate) throws RemoteException {
        return contractService.updateContractEndDate(contractId, endDate);
    }
    
    public boolean isContractActive(int contractId) throws RemoteException {
        return contractService.isContractActive(contractId);
    }

    public List<EmployeeContract> getContractsByNationalId(String nationalId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

