/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author PC
 */

import java.util.Date;
import java.util.List;
import model.EmployeeContract;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeContractDao {
    
    // SAVE/CREATE
    public EmployeeContract saveContract(EmployeeContract contractObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(contractObj);
            tr.commit();
            ss.close();
            return contractObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public EmployeeContract updateContract(EmployeeContract contractObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(contractObj);
            tr.commit();
            ss.close();
            return contractObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public EmployeeContract deleteContract(EmployeeContract contractObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(contractObj);
            tr.commit();
            ss.close();
            return contractObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE BY CONTRACT ID
    public boolean deleteContractById(int contractId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            
            Object contract = ss.get(EmployeeContract.class, contractId);
            if (contract != null) {
                ss.delete(contract);
                tr.commit();
                ss.close();
                return true;
            }
            ss.close();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // FIND BY ID (using EmployeeContract object)
    public EmployeeContract findContractById(EmployeeContract contractObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            EmployeeContract theContract = (EmployeeContract) ss.get(EmployeeContract.class, contractObj.getContractId());
            ss.close();
            return theContract;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY CONTRACT ID (int)
    public EmployeeContract findById(int contractId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            EmployeeContract contract = (EmployeeContract) ss.get(EmployeeContract.class, contractId);
            ss.close();
            return contract;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY EMPLOYEE ID
    public EmployeeContract findByEmployeeId(int employeeId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM EmployeeContract ec WHERE ec.employee.employeeId = :empId");
            query.setParameter("empId", employeeId);
            EmployeeContract contract = (EmployeeContract) query.uniqueResult();
            ss.close();
            return contract;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL CONTRACTS
    public List<EmployeeContract> findAllContracts() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<EmployeeContract> contracts = ss.createQuery("FROM EmployeeContract").list();
            ss.close();
            return contracts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND CONTRACTS BY CONTRACT TYPE
    public List<EmployeeContract> findByContractType(String contractType) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM EmployeeContract ec WHERE ec.contractType = :type");
            query.setParameter("type", EmployeeContract.ContractType.valueOf(contractType));
            List<EmployeeContract> contracts = query.list();
            ss.close();
            return contracts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ACTIVE CONTRACTS (no end date or end date in future)
    public List<EmployeeContract> findActiveContracts() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM EmployeeContract ec WHERE ec.endDate IS NULL OR ec.endDate >= CURRENT_DATE");
            List<EmployeeContract> contracts = query.list();
            ss.close();
            return contracts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND EXPIRED CONTRACTS (end date in past)
    public List<EmployeeContract> findExpiredContracts() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM EmployeeContract ec WHERE ec.endDate < CURRENT_DATE");
            List<EmployeeContract> contracts = query.list();
            ss.close();
            return contracts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND CONTRACTS FOR SPECIFIC USER/FARMER
    public List<EmployeeContract> findContractsByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT ec FROM EmployeeContract ec " +
                                        "JOIN ec.employee e " +
                                        "JOIN e.user u " +
                                        "WHERE u.nationalId = :nid");
            query.setParameter("nid", nationalId);
            List<EmployeeContract> contracts = query.list();
            ss.close();
            return contracts;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // COUNT ALL CONTRACTS
    public Long countAllContracts() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(ec) FROM EmployeeContract ec");
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // COUNT ACTIVE CONTRACTS FOR USER
    public Long countActiveContractsByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(ec) FROM EmployeeContract ec " +
                                        "JOIN ec.employee e " +
                                        "JOIN e.user u " +
                                        "WHERE u.nationalId = :nid " +
                                        "AND (ec.endDate IS NULL OR ec.endDate >= CURRENT_DATE)");
            query.setParameter("nid", nationalId);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // CHECK IF CONTRACT EXISTS FOR EMPLOYEE
    public boolean contractExistsForEmployee(int employeeId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(ec) FROM EmployeeContract ec WHERE ec.employee.employeeId = :empId");
            query.setParameter("empId", employeeId);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null && count > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // UPDATE CONTRACT END DATE (for contract termination)
    public boolean updateContractEndDate(int contractId, Date endDate) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            
            Query query = ss.createQuery("UPDATE EmployeeContract SET endDate = :endDate WHERE contractId = :cid");
            query.setParameter("endDate", endDate);
            query.setParameter("cid", contractId);
            
            int updated = query.executeUpdate();
            tr.commit();
            ss.close();
            
            return updated > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}