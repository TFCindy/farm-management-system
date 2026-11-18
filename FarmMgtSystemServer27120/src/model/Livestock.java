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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "livestock")
public class Livestock implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private int animalId; // Auto-generated primary key
    
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "national_id", referencedColumnName = "national_id", nullable = false)
    private User user; // This creates the foreign key relationship
    
    @Column(name = "animal_type", nullable = false, length = 50)
    private String animalType;
    
    @Column(name = "breed", length = 50)
    private String breed;
    
    @Column(name = "age")
    private int age;
    
    @Column(name = "health_status", length = 20)
    private String healthStatus;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    // Constructors
    public Livestock() {
    }

    // Constructor for creating new livestock (without animalId)
    public Livestock(User user, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.user = user;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.quantity = quantity;
    }
    
    // Constructor with all fields including animalId
    public Livestock(int animalId, User user, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.animalId = animalId;
        this.user = user;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.quantity = quantity;
    }
    
    // Constructor for backward compatibility (with String nationalId)
    // This is useful if you have existing code that uses String nationalId
    public Livestock(int animalId, String nationalId, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.animalId = animalId;
        // Create a User object with just the nationalId for reference
        this.user = new User(nationalId);
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getAnimalId() { 
        return animalId; 
    }
    
    public void setAnimalId(int animalId) { 
        this.animalId = animalId; 
    }

    public User getUser() { 
        return user; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }
    
    // Helper method to get nationalId from User object
    public String getNationalId() { 
        return user != null ? user.getNationalId() : null; 
    }
    
    // Helper method to set nationalId by creating/updating User object
    public void setNationalId(String nationalId) { 
        if (this.user == null) {
            this.user = new User(nationalId);
        } else {
            this.user.setNationalId(nationalId);
        }
    }

    public String getAnimalType() { 
        return animalType; 
    }
    
    public void setAnimalType(String animalType) { 
        this.animalType = animalType; 
    }

    public String getBreed() { 
        return breed; 
    }
    
    public void setBreed(String breed) { 
        this.breed = breed; 
    }

    public int getAge() { 
        return age; 
    }
    
    public void setAge(int age) { 
        this.age = age; 
    }

    public String getHealthStatus() { 
        return healthStatus; 
    }
    
    public void setHealthStatus(String healthStatus) { 
        this.healthStatus = healthStatus; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    
    // Override equals and hashCode based on animalId
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Livestock livestock = (Livestock) obj;
        return animalId == livestock.animalId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(animalId);
    }
    
    // Override toString for debugging
    @Override
    public String toString() {
        return "Livestock{" + 
                "animalId=" + animalId + 
                ", nationalId=" + (user != null ? user.getNationalId() : "null") + 
                ", animalType=" + animalType + 
                ", breed=" + breed + 
                ", age=" + age + 
                ", healthStatus=" + healthStatus + 
                ", quantity=" + quantity + 
                '}';
    }
}
