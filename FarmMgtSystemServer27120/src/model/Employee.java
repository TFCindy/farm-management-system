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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "employee_id", nullable = false, unique = true)
    private int employeeId; // Manually assigned primary key (not auto-generated)
    
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "national_id", referencedColumnName = "national_id", nullable = false)
    private User user; // Foreign key relationship with users table
    
    // Transient field to accept DTO-style payloads from the client
    @Transient
    private String nationalId;
    
     @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmployeeContract contract;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "contact", nullable = false, length = 20)
    private String contact;
    
    @Column(name = "role", nullable = false, length = 50)
    private String role;
    
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private double salary;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;
    
    // Constructors
    public Employee() {
    }

    // Constructor for creating new employee
    public Employee(int employeeId, User user, String name, String contact, String role, double salary, Date hireDate) {
        this.employeeId = employeeId;
        this.user = user;
        this.name = name;
        this.contact = contact;
        this.role = role;
        this.salary = salary;
        this.hireDate = hireDate;
    }
    
    // Constructor for backward compatibility (with String nationalId)
    public Employee(int employeeId, String nationalId, String name, String contact, String role, double salary, Date hireDate) {
        this.employeeId = employeeId;
        // Create a User object with just the nationalId for reference
        this.user = new User(nationalId);
        this.name = name;
        this.contact = contact;
        this.role = role;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters
    public int getEmployeeId() { 
        return employeeId; 
    }
    
    public void setEmployeeId(int employeeId) { 
        this.employeeId = employeeId; 
    }

    public User getUser() { 
        return user; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }
    
    // Helper method to get nationalId from User object
    public String getNationalId() { 
        if (user != null && user.getNationalId() != null) return user.getNationalId();
        return nationalId != null ? nationalId : null;
    }
    
    // Helper method to set nationalId by creating/updating User object
    public void setNationalId(String nationalId) { 
        this.nationalId = nationalId;
        if (nationalId == null) return;
        if (this.user == null) {
            this.user = new User(nationalId);
        } else {
            this.user.setNationalId(nationalId);
        }
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
    
    // Override equals and hashCode based on employeeId
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(employeeId);
    }
    
    // Override toString for debugging
    @Override
    public String toString() {
        return "Employee{" + 
                "employeeId=" + employeeId + 
                ", nationalId=" + (user != null ? user.getNationalId() : "null") + 
                ", name=" + name + 
                ", contact=" + contact + 
                ", role=" + role + 
                ", salary=" + salary + 
                ", hireDate=" + hireDate + 
                '}';
    }
}
