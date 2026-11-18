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
import java.util.HashSet;
import java.util.Set;

public class Crop implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int cropId;
    private String nationalId; // Just store the ID, not the full User object
    private String cropName;
    private Date plantingDate;
    private Date harvestDate;
    private double expectedYield;
    private String status;
    private Set<Integer> fertilizerIds = new HashSet<>(); // Store only IDs, not full objects
    
    // Constructors
    public Crop() {
    }

    // Getters and Setters ONLY
    public int getCropId() { 
        return cropId; 
    }
    
    public void setCropId(int cropId) { 
        this.cropId = cropId; 
    }

    public String getNationalId() { 
        return nationalId; 
    }
    
    public void setNationalId(String nationalId) { 
        this.nationalId = nationalId; 
    }

    public String getCropName() { 
        return cropName; 
    }
    
    public void setCropName(String cropName) { 
        this.cropName = cropName;  
    }

    public Date getPlantingDate() { 
        return plantingDate; 
    }
    
    public void setPlantingDate(Date plantingDate) { 
        this.plantingDate = plantingDate; 
    }

    public Date getHarvestDate() { 
        return harvestDate; 
    }
    
    public void setHarvestDate(Date harvestDate) { 
        this.harvestDate = harvestDate; 
    }

    public double getExpectedYield() { 
        return expectedYield; 
    }
    
    public void setExpectedYield(double expectedYield) { 
        this.expectedYield = expectedYield; 
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public Set<Integer> getFertilizerIds() { 
        return fertilizerIds; 
    }
    
    public void setFertilizerIds(Set<Integer> fertilizerIds) { 
        this.fertilizerIds = fertilizerIds; 
    }
}
