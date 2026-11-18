package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.Employee;
import service.EmployeeService;

/**
 * Controller for Employee-related operations
 * Handles communication between View and RMI Service
 */
public class EmployeeController {
    
    private EmployeeService employeeService;
    
    public EmployeeController() throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        this.employeeService = (EmployeeService) registry.lookup("EmployeeService");
    }
    
    /**
     * Save new employee
     */
    public Employee saveEmployee(Employee employee) throws RemoteException {
        return employeeService.saveEmployee(employee);
    }
    
    /**
     * Update existing employee
     */
    public Employee updateEmployee(Employee employee) throws RemoteException {
        return employeeService.updateEmployee(employee);
    }
    
    /**
     * Delete employee
     */
    public Employee deleteEmployee(Employee employee) throws RemoteException {
        return employeeService.deleteEmployee(employee);
    }
    
    /**
     * Search employee by ID
     */
    public Employee searchEmployeeById(Employee employee) throws RemoteException {
        return employeeService.searchEmployeeById(employee);
    }
    
    /**
     * Retrieve all employees
     */
    public List<Employee> retrieveAllEmployees() throws RemoteException {
        return employeeService.retrieveAllEmployees();
    }
    
    /**
     * Find employees by user (nationalId)
     */
    public List<Employee> findEmployeesByUser(String nationalId) throws RemoteException {
        return employeeService.findEmployeesByUser(nationalId);
    }
    
    /**
     * Search employees by name
     */
    public List<Employee> searchEmployeesByName(String nationalId, String name) throws RemoteException {
        return employeeService.searchEmployeesByName(nationalId, name);
    }
    
    /**
     * Count employees by user
     */
    public Long countEmployeesByUser(String nationalId) throws RemoteException {
        return employeeService.countEmployeesByUser(nationalId);
    }
    
    /**
     * Count all employees
     */
    public Long countAllEmployees() throws RemoteException {
        return employeeService.countAllEmployees();
    }
    
    /**
     * Check if employee exists
     */
    public boolean employeeExists(int employeeId) throws RemoteException {
        return employeeService.employeeExists(employeeId);
    }
    
    /**
     * Helper method: Delete employee by ID and nationalId
     * Creates an Employee object with the ID and calls deleteEmployee
     */
    public boolean deleteEmployeeById(int employeeId, String nationalId) throws RemoteException {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setNationalId(nationalId);
        Employee deleted = deleteEmployee(employee);
        return deleted != null;
    }
    
    /**
     * Helper method: Create employee (for compatibility with views expecting int return)
     */
    public int createEmployee(Employee employee) throws RemoteException {
        Employee saved = saveEmployee(employee);
        return saved != null ? 1 : 0;
    }
    
    /**
     * Helper method: Update employee (for compatibility with views expecting int return)
     */
    public int updateEmployeeById(Employee employee) throws RemoteException {
        Employee updated = updateEmployee(employee);
        return updated != null ? 1 : 0;
    }
}

