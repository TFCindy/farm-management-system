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
import java.util.List;
import model.Employee;


public interface EmployeeService extends Remote {
    public Employee saveEmployee(Employee theEmployee) throws RemoteException;
    public Employee updateEmployee(Employee theEmployee) throws RemoteException;
    public Employee deleteEmployee(Employee theEmployee) throws RemoteException;
    public Employee searchEmployeeById(Employee theEmployee) throws RemoteException;
    public List<Employee> retrieveAllEmployees() throws RemoteException;
    public List<Employee> findEmployeesByUser(String nationalId) throws RemoteException;
    public List<Employee> searchEmployeesByName(String nationalId, String name) throws RemoteException;
    public Long countEmployeesByUser(String nationalId) throws RemoteException;
    public Long countAllEmployees() throws RemoteException;
    public boolean employeeExists(int employeeId) throws RemoteException;
}
