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
import javax.persistence.*;

@Entity
@Table(name = "fertilizers")
public class Fertilizer implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fertilizer_id")
    private int fertilizerId;
    
    @Column(name = "fertilizer_name", nullable = false, unique = true, length = 100)
    private String fertilizerName;
    
    @Column(name = "fertilizer_type", length = 30)
    private String fertilizerType; // Organic, Inorganic
    
    @Column(name = "description", length = 500)
    private String description;
    
    @ManyToMany(mappedBy = "fertilizers")
    private Set<Crop> crops = new HashSet<>();
    
    // Constructors
    public Fertilizer() {
    }
    
    public Fertilizer(String fertilizerName, String fertilizerType) {
        this.fertilizerName = fertilizerName;
        this.fertilizerType = fertilizerType;
    }
    
    public Fertilizer(int fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    // Getters and Setters
    public int getFertilizerId() { return fertilizerId; }
    public void setFertilizerId(int fertilizerId) { this.fertilizerId = fertilizerId; }

    public String getFertilizerName() { return fertilizerName; }
    public void setFertilizerName(String fertilizerName) { this.fertilizerName = fertilizerName; }

    public String getFertilizerType() { return fertilizerType; }
    public void setFertilizerType(String fertilizerType) { this.fertilizerType = fertilizerType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Crop> getCrops() { return crops; }
    public void setCrops(Set<Crop> crops) { this.crops = crops; }
    
    // Helper method to add crop
    public void addCrop(Crop crop) {
        this.crops.add(crop);
        crop.getFertilizers().add(this);
    }
    
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
    
    @Override
    public String toString() {
        return fertilizerName + (fertilizerType != null ? " (" + fertilizerType + ")" : "");
    }
}
