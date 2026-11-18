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

import java.util.List;
import model.Employee;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class EmployeeDao {
    
    // SAVE/CREATE
    public Employee saveEmployee(Employee employeeObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // Ensure referenced User is managed
            String nid = employeeObj.getNationalId();
            if (nid == null) {
                System.out.println("EmployeeDao.saveEmployee: missing nationalId on employee");
                ss.close();
                return null;
            }
            model.User user = (model.User) ss.get(model.User.class, nid);
            if (user == null) {
                // fallback: case-insensitive/trim match
                String nn = nid == null ? null : nid.trim().toLowerCase();
                try {
                    Query uq = ss.createQuery("FROM User u WHERE LOWER(u.nationalId) = :nid");
                    uq.setParameter("nid", nn);
                    user = (model.User) uq.uniqueResult();
                } catch (Exception qex) {
                }
            }
            if (user == null) {
                System.out.println("EmployeeDao.saveEmployee: user not found for nationalId='" + nid + "'");
                ss.close();
                return null;
            }
            employeeObj.setUser(user);
            // Assign employeeId if not provided (manual PK)
            if (employeeObj.getEmployeeId() <= 0) {
                Query q = ss.createQuery("SELECT MAX(e.employeeId) FROM Employee e");
                Number max = (Number) q.uniqueResult();
                int next = (max == null) ? 1 : max.intValue() + 1;
                employeeObj.setEmployeeId(next);
            }
            Transaction tr = ss.beginTransaction();
            ss.save(employeeObj);
            tr.commit();
            ss.close();
            return employeeObj;
        } catch (Exception ex) {
            System.err.println("EmployeeDao.saveEmployee: exception while saving employee: " + ex.getMessage());
            try { System.err.println("Employee data: " + employeeObj); } catch (Exception ignore) {}
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public Employee updateEmployee(Employee employeeObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // Ensure referenced User is managed
            String nid = employeeObj.getNationalId();
            if (nid == null) {
                System.out.println("EmployeeDao.updateEmployee: missing nationalId on employee");
                ss.close();
                return null;
            }
            model.User user = (model.User) ss.get(model.User.class, nid);
            if (user == null) {
                String nn = nid == null ? null : nid.trim().toLowerCase();
                try {
                    Query uq = ss.createQuery("FROM User u WHERE LOWER(u.nationalId) = :nid");
                    uq.setParameter("nid", nn);
                    user = (model.User) uq.uniqueResult();
                } catch (Exception qex) {
                }
            }
            if (user == null) {
                System.out.println("EmployeeDao.updateEmployee: user not found for nationalId='" + nid + "'");
                ss.close();
                return null;
            }
            employeeObj.setUser(user);
            Transaction tr = ss.beginTransaction();
            ss.update(employeeObj);
            tr.commit();
            ss.close();
            return employeeObj;
        } catch (Exception ex) {
            System.err.println("EmployeeDao.updateEmployee: exception while updating employee: " + ex.getMessage());
            try { System.err.println("Employee data: " + employeeObj); } catch (Exception ignore) {}
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public Employee deleteEmployee(Employee employeeObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(employeeObj);
            tr.commit();
            ss.close();
            return employeeObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ID (using Employee object)
    public Employee findEmployeeById(Employee employeeObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Employee theEmployee = (Employee) ss.get(Employee.class, employeeObj.getEmployeeId());
            ss.close();
            return theEmployee;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL
    public List<Employee> findAllEmployees() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Employee> employees = ss.createQuery("FROM Employee").list();
            ss.close();
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY NATIONAL ID (get employees for specific user)
    public List<Employee> findEmployeesByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Employee e WHERE e.user.nationalId = :nid ORDER BY e.employeeId DESC");
            query.setParameter("nid", nationalId);
            List<Employee> employees = query.list();
            ss.close();
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // SEARCH BY NAME for specific user
    public List<Employee> searchEmployeesByName(String nationalId, String name) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Employee e WHERE e.user.nationalId = :nid AND LOWER(e.name) LIKE LOWER(:name) ORDER BY e.employeeId DESC");
            query.setParameter("nid", nationalId);
            query.setParameter("name", "%" + name + "%");
            List<Employee> employees = query.list();
            ss.close();
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY EMPLOYEE ID for specific user
    public Employee findEmployeeByIdAndUser(int employeeId, String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Employee e WHERE e.employeeId = :eid AND e.user.nationalId = :nid");
            query.setParameter("eid", employeeId);
            query.setParameter("nid", nationalId);
            Employee employee = (Employee) query.uniqueResult();
            ss.close();
            return employee;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // COUNT employees for specific user
    public Long countEmployeesByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.user.nationalId = :nid");
            query.setParameter("nid", nationalId);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // COUNT ALL employees
    public Long countAllEmployees() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(e) FROM Employee e");
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // FIND RECENT employees for user
    public List<Employee> findRecentEmployeesByUser(String nationalId, int limit) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Employee e WHERE e.user.nationalId = :nid ORDER BY e.employeeId DESC");
            query.setParameter("nid", nationalId);
            query.setMaxResults(limit);
            List<Employee> employees = query.list();
            ss.close();
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // CHECK if employee exists by ID
    public boolean employeeExists(int employeeId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Employee employee = (Employee) ss.get(Employee.class, employeeId);
            ss.close();
            return employee != null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
