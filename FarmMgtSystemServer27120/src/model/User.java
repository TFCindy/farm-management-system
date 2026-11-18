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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "national_id", unique = true, nullable = false, length = 20)
    private String nationalId;
    
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;
    
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Column(name = "district", nullable = false, length = 50)
    private String district;
    
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "phone_number", unique = true, nullable = false, length = 20)
    private String phoneNumber;
    
    // ✅ ADDED: One-to-Many relationship with Livestock
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Livestock> livestockList = new ArrayList<>();
    
    // ✅ ADDED: One-to-Many relationship with Crop
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Crop> crops = new ArrayList<>();
    
    // ✅ ADDED: One-to-Many relationship with Employee
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
    
    // Constructors
    public User() {
    }

    // Full constructor
    public User(String nationalId, String firstname, String lastname, 
                String password, String district, String email, String phoneNumber) {
        this.nationalId = nationalId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.district = district;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    // Constructor for searching by nationalId
    public User(String nationalId) {
        this.nationalId = nationalId;
    }

    // Getters and Setters
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    // ✅ ADDED: Getter and Setter for livestockList
    public List<Livestock> getLivestockList() { return livestockList; }
    public void setLivestockList(List<Livestock> livestockList) { this.livestockList = livestockList; }
    
    // ✅ ADDED: Getter and Setter for crops
    public List<Crop> getCrops() { return crops; }
    public void setCrops(List<Crop> crops) { this.crops = crops; }
    
    // ✅ ADDED: Getter and Setter for employees
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    
    // Helper methods to manage relationships
    // ✅ ADDED: Helper method to add livestock
    public void addLivestock(Livestock livestock) {
        livestockList.add(livestock);
        livestock.setUser(this);
    }
    
    // ✅ ADDED: Helper method to remove livestock
    public void removeLivestock(Livestock livestock) {
        livestockList.remove(livestock);
        livestock.setUser(null);
    }
    
    // ✅ ADDED: Helper method to add crop
    public void addCrop(Crop crop) {
        crops.add(crop);
        crop.setUser(this);
    }
    
    // ✅ ADDED: Helper method to remove crop
    public void removeCrop(Crop crop) {
        crops.remove(crop);
        crop.setUser(null);
    }
    
    // ✅ ADDED: Helper method to add employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setUser(this);
    }
    
    // ✅ ADDED: Helper method to remove employee
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setUser(null);
    }
    
    // Helper method to check if string is email (simple check)
    public static boolean isEmail(String input) {
        return input != null && input.contains("@") && input.contains(".");
    }
    
    // Helper method to check if string is phone number (simple check)
    public static boolean isPhoneNumber(String input) {
        return input != null && input.matches("\\d+") && input.length() >= 10;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return nationalId != null ? nationalId.equals(user.nationalId) : user.nationalId == null;
    }

    @Override
    public int hashCode() {
        return nationalId != null ? nationalId.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "User{" + 
                "nationalId=" + nationalId + 
                ", firstname=" + firstname + 
                ", lastname=" + lastname + 
                ", email=" + email + 
                ", phoneNumber=" + phoneNumber + 
                ", district=" + district + 
                '}';
    }
}