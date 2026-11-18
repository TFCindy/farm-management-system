/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PC
 */

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int employeeId;
    private String nationalId; // Store just the ID, not the full User object
    private String name;
    private String contact;
    private String role;
    private double salary;
    private Date hireDate;
    
    // Default constructor
    public Employee() {
    }

    // Constructor with all fields
    public Employee(int employeeId, String nationalId, String name, String contact, 
                   String role, double salary, Date hireDate) {
        this.employeeId = employeeId;
        this.nationalId = nationalId;
        this.name = name;
        this.contact = contact;
        this.role = role;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters ONLY
    public int getEmployeeId() { 
        return employeeId; 
    }
    
    public void setEmployeeId(int employeeId) { 
        this.employeeId = employeeId; 
    }

    public String getNationalId() { 
        return nationalId; 
    }
    
    public void setNationalId(String nationalId) { 
        this.nationalId = nationalId; 
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getContact() { 
        return contact; 
    }
    
    public void setContact(String contact) { 
        this.contact = contact; 
    }

    public String getRole() { 
        return role; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }

    public double getSalary() { 
        return salary; 
    }
    
    public void setSalary(double salary) { 
        this.salary = salary; 
    }

    public Date getHireDate() { 
        return hireDate; 
    }
    
    public void setHireDate(Date hireDate) { 
        this.hireDate = hireDate; 
    }
}
