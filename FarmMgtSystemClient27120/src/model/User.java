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

public class User implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private String nationalId;
    private String firstname;
    private String lastname;
    private String password;
    private String district;
    private String email;
    private String phoneNumber;
    
    // Constructors
    public User() {
    }

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
    
    public User(String nationalId) {
        this.nationalId = nationalId;
    }

    // Getters and Setters ONLY
    public String getNationalId() { 
        return nationalId; 
    }
    
    public void setNationalId(String nationalId) { 
        this.nationalId = nationalId; 
    }

    public String getFirstname() { 
        return firstname; 
    }
    
    public void setFirstname(String firstname) { 
        this.firstname = firstname; 
    }

    public String getLastname() { 
        return lastname; 
    }
    
    public void setLastname(String lastname) { 
        this.lastname = lastname; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getDistrict() { 
        return district; 
    }
    
    public void setDistrict(String district) { 
        this.district = district; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    // Basic equals and hashCode for collections
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
    
    // Simple toString for debugging
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
