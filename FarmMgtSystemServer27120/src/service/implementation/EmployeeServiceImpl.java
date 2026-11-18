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
import java.util.List;
import model.Employee;
import dao.EmployeeDao;
import service.EmployeeService;

public class EmployeeServiceImpl extends UnicastRemoteObject implements EmployeeService {
    
    private EmployeeDao employeeDao;
    
    public EmployeeServiceImpl() throws RemoteException {
        super();
        employeeDao = new EmployeeDao();
    }
    
    @Override
    public Employee saveEmployee(Employee theEmployee) throws RemoteException {
        try {
            return employeeDao.saveEmployee(theEmployee);
        } catch (Exception e) {
            throw new RemoteException("Error saving employee: " + e.getMessage());
        }
    }
    
    @Override
    public Employee updateEmployee(Employee theEmployee) throws RemoteException {
        try {
            return employeeDao.updateEmployee(theEmployee);
        } catch (Exception e) {
            throw new RemoteException("Error updating employee: " + e.getMessage());
        }
    }
    
    @Override
    public Employee deleteEmployee(Employee theEmployee) throws RemoteException {
        try {
            return employeeDao.deleteEmployee(theEmployee);
        } catch (Exception e) {
            throw new RemoteException("Error deleting employee: " + e.getMessage());
        }
    }
    
    @Override
    public Employee searchEmployeeById(Employee theEmployee) throws RemoteException {
        try {
            return employeeDao.findEmployeeById(theEmployee);
        } catch (Exception e) {
            throw new RemoteException("Error finding employee: " + e.getMessage());
        }
    }
    
    @Override
    public List<Employee> retrieveAllEmployees() throws RemoteException {
        try {
            return employeeDao.findAllEmployees();
        } catch (Exception e) {
            throw new RemoteException("Error retrieving employees: " + e.getMessage());
        }
    }
    
    @Override
    public List<Employee> findEmployeesByUser(String nationalId) throws RemoteException {
        try {
            return employeeDao.findEmployeesByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error finding employees for user: " + e.getMessage());
        }
    }
    
    @Override
    public List<Employee> searchEmployeesByName(String nationalId, String name) throws RemoteException {
        try {
            return employeeDao.searchEmployeesByName(nationalId, name);
        } catch (Exception e) {
            throw new RemoteException("Error searching employees: " + e.getMessage());
        }
    }
    
    @Override
    public Long countEmployeesByUser(String nationalId) throws RemoteException {
        try {
            return employeeDao.countEmployeesByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error counting employees: " + e.getMessage());
        }
    }
    
    @Override
    public Long countAllEmployees() throws RemoteException {
        try {
            return employeeDao.countAllEmployees();
        } catch (Exception e) {
            throw new RemoteException("Error counting all employees: " + e.getMessage());
        }
    }
    
    @Override
    public boolean employeeExists(int employeeId) throws RemoteException {
        try {
            return employeeDao.employeeExists(employeeId);
        } catch (Exception e) {
            throw new RemoteException("Error checking employee existence: " + e.getMessage());
        }
    }
}
