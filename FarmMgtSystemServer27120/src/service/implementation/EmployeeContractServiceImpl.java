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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import model.EmployeeContract;
import dao.EmployeeContractDao;
import dao.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import service.EmployeeContractService;

public class EmployeeContractServiceImpl extends UnicastRemoteObject implements EmployeeContractService {
    
    private EmployeeContractDao contractDao;
    
    public EmployeeContractServiceImpl() throws RemoteException {
        super();
        contractDao = new EmployeeContractDao();
    }
    
    @Override
    public EmployeeContract saveContract(EmployeeContract theContract) throws RemoteException {
        try {
            return contractDao.saveContract(theContract);
        } catch (Exception e) {
            throw new RemoteException("Error saving contract: " + e.getMessage());
        }
    }
    
    @Override
    public EmployeeContract updateContract(EmployeeContract theContract) throws RemoteException {
        try {
            return contractDao.updateContract(theContract);
        } catch (Exception e) {
            throw new RemoteException("Error updating contract: " + e.getMessage());
        }
    }
    
    @Override
    public EmployeeContract deleteContract(EmployeeContract theContract) throws RemoteException {
        try {
            return contractDao.deleteContract(theContract);
        } catch (Exception e) {
            throw new RemoteException("Error deleting contract: " + e.getMessage());
        }
    }
    
    @Override
    public EmployeeContract searchContractById(EmployeeContract theContract) throws RemoteException {
        try {
            return contractDao.findContractById(theContract);
        } catch (Exception e) {
            throw new RemoteException("Error finding contract: " + e.getMessage());
        }
    }
    
    @Override
    public List<EmployeeContract> retrieveAllContracts() throws RemoteException {
        try {
            return contractDao.findAllContracts();
        } catch (Exception e) {
            throw new RemoteException("Error retrieving contracts: " + e.getMessage());
        }
    }
    
    @Override
    public EmployeeContract findById(int contractId) throws RemoteException {
        try {
            return contractDao.findById(contractId);
        } catch (Exception e) {
            throw new RemoteException("Error finding contract by ID: " + e.getMessage());
        }
    }
    
    @Override
    public EmployeeContract findByEmployeeId(int employeeId) throws RemoteException {
        try {
            return contractDao.findByEmployeeId(employeeId);
        } catch (Exception e) {
            throw new RemoteException("Error finding contract for employee: " + e.getMessage());
        }
    }
    
    @Override
    public List<EmployeeContract> findByContractType(String contractType) throws RemoteException {
        try {
            return contractDao.findByContractType(contractType);
        } catch (Exception e) {
            throw new RemoteException("Error finding contracts by type: " + e.getMessage());
        }
    }
    
    @Override
    public List<EmployeeContract> findActiveContracts() throws RemoteException {
        try {
            return contractDao.findActiveContracts();
        } catch (Exception e) {
            throw new RemoteException("Error finding active contracts: " + e.getMessage());
        }
    }
    
    @Override
    public List<EmployeeContract> findExpiredContracts() throws RemoteException {
        try {
            return contractDao.findExpiredContracts();
        } catch (Exception e) {
            throw new RemoteException("Error finding expired contracts: " + e.getMessage());
        }
    }
    
    @Override
    public List<EmployeeContract> findContractsByUser(String nationalId) throws RemoteException {
        try {
            return contractDao.findContractsByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error finding contracts for user: " + e.getMessage());
        }
    }
    
    @Override
    public Long countAllContracts() throws RemoteException {
        try {
            return contractDao.countAllContracts();
        } catch (Exception e) {
            throw new RemoteException("Error counting contracts: " + e.getMessage());
        }
    }
    
    @Override
    public Long countActiveContractsByUser(String nationalId) throws RemoteException {
        try {
            return contractDao.countActiveContractsByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error counting active contracts: " + e.getMessage());
        }
    }
    
    @Override
    public boolean contractExistsForEmployee(int employeeId) throws RemoteException {
        try {
            return contractDao.contractExistsForEmployee(employeeId);
        } catch (Exception e) {
            throw new RemoteException("Error checking contract existence: " + e.getMessage());
        }
    }
    
    @Override
    public boolean updateContractEndDate(int contractId, Date endDate) throws RemoteException {
        try {
            return contractDao.updateContractEndDate(contractId, endDate);
        } catch (Exception e) {
            throw new RemoteException("Error updating contract end date: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isContractActive(int contractId) throws RemoteException {
        try {
            EmployeeContract contract = contractDao.findById(contractId);
            if (contract != null) {
                return contract.isActive();
            }
            return false;
        } catch (Exception e) {
            throw new RemoteException("Error checking contract status: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeContract> getContractsByNationalId(String nationalId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery(
        "FROM EmployeeContract ec WHERE ec.user.nationalId = :nid"
    );
    query.setParameter("nid", nationalId);
    List<EmployeeContract> list = query.list();
    session.close();
    return list;
}

}
