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
import java.util.HashSet;
import java.util.Set;

public class Fertilizer implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int fertilizerId;
    private String fertilizerName;
    private String fertilizerType; // Organic, Inorganic
    private String description;
    
    // Constructors
    public Fertilizer() {
    }
    
    public Fertilizer(String fertilizerName, String fertilizerType) {
        this.fertilizerName = fertilizerName;
        this.fertilizerType = fertilizerType;
    }
    
    public Fertilizer(String fertilizerName, String fertilizerType, String description) {
        this.fertilizerName = fertilizerName;
        this.fertilizerType = fertilizerType;
        this.description = description;
    }
    
    public Fertilizer(int fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    // Getters and Setters
    public int getFertilizerId() { 
        return fertilizerId; 
    }
    
    public void setFertilizerId(int fertilizerId) { 
        this.fertilizerId = fertilizerId; 
    }

    public String getFertilizerName() { 
        return fertilizerName; 
    }
    
    public void setFertilizerName(String fertilizerName) { 
        this.fertilizerName = fertilizerName; 
    }

    public String getFertilizerType() { 
        return fertilizerType; 
    }
    
    public void setFertilizerType(String fertilizerType) { 
        this.fertilizerType = fertilizerType; 
    }

    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    // Basic equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fertilizer that = (Fertilizer) obj;
        return fertilizerId == that.fertilizerId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(fertilizerId);
    }
    
    // Simple toString for debugging
    @Override
    public String toString() {
        return fertilizerName + (fertilizerType != null ? " (" + fertilizerType + ")" : "");
    }
}