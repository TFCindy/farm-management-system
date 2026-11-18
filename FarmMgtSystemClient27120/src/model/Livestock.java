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

public class Livestock implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int animalId;
    private User user; // Reference to the User object
    private String animalType;
    private String breed;
    private int age;
    private String healthStatus;
    private int quantity;
    
    // Constructors
    public Livestock() {
    }

    // Constructor without animalId (for new records)
    public Livestock(User user, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.user = user;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.quantity = quantity;
    }
    
    // Constructor with all fields
    public Livestock(int animalId, User user, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.animalId = animalId;
        this.user = user;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.quantity = quantity;
    }
    
    // Constructor for backward compatibility (with String nationalId instead of User object)
    public Livestock(int animalId, String nationalId, String animalType, String breed, int age, String healthStatus, int quantity) {
        this.animalId = animalId;
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
    
    // Basic equals and hashCode
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
    
    // Simple toString for debugging
    @Override
    public String toString() {
        return "Livestock{" + 
                "animalId=" + animalId + 
                ", user=" + (user != null ? user.getNationalId() : "null") + 
                ", animalType=" + animalType + 
                ", breed=" + breed + 
                ", age=" + age + 
                ", healthStatus=" + healthStatus + 
                ", quantity=" + quantity + 
                '}';
    }
}