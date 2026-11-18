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
import javax.persistence.*;

@Entity
@Table(name = "crops")
public class Crop implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_id")
    private int cropId;
    
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "national_id", referencedColumnName = "national_id", nullable = false)
    private User user;
    
    // Transient field to accept DTO-style payloads from the client that carry
    // a simple `nationalId` String instead of a full `User` object. This field
    // is NOT persisted by Hibernate (marked @Transient) but will be populated
    // during Java serialization when the client sends a Crop with a `nationalId` field.
    @Transient
    private String nationalId;
    
    @Column(name = "crop_name", nullable = false, length = 100)
    private String cropName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "planting_date", nullable = false)
    private Date plantingDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "harvest_date")
    private Date harvestDate;
    
    @Column(name = "expected_yield", precision = 10, scale = 2)
    private double expectedYield;
    
    @Column(name = "status", length = 20)
    private String status;
    
    // ✅ Many-to-Many relationship with Fertilizer
    @ManyToMany
    @JoinTable(
        name = "crop_fertilizers",
        joinColumns = @JoinColumn(name = "crop_id"),
        inverseJoinColumns = @JoinColumn(name = "fertilizer_id")
    )
    private Set<Fertilizer> fertilizers = new HashSet<>();
    
    // Constructors
    public Crop() {
    }

    public Crop(User user, String cropName, Date plantingDate, Date harvestDate, 
                double expectedYield, String status) {
        this.user = user;
        this.cropName = cropName;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.expectedYield = expectedYield;
        this.status = status;
    }
    
    public Crop(int cropId, User user, String cropName, Date plantingDate, 
                Date harvestDate, double expectedYield, String status) {
        this.cropId = cropId;
        this.user = user;
        this.cropName = cropName;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.expectedYield = expectedYield;
        this.status = status;
    }
    
    public Crop(int cropId, String nationalId, String cropName, Date plantingDate, 
                Date harvestDate, double expectedYield, String status) {
        this.cropId = cropId;
        this.user = new User(nationalId);
        this.cropName = cropName;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.expectedYield = expectedYield;
        this.status = status;
    }

    // Getters and Setters
    public int getCropId() { 
        return cropId; 
    }
    
    public void setCropId(int cropId) { 
        this.cropId = cropId; 
    }

    public User getUser() { 
        return user; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }
    
    public String getNationalId() { 
        // Prefer the managed User's id when available; otherwise fall back to
        // the transient DTO field populated by the client.
        if (user != null && user.getNationalId() != null) return user.getNationalId();
        return nationalId != null ? nationalId : null;
    }
    
    public void setNationalId(String nationalId) { 
        // Keep the transient field in sync so DTO-only clients work, and also
        // attach to the user when appropriate.
        this.nationalId = nationalId;
        if (nationalId == null) return;
        if (this.user == null) {
            this.user = new User(nationalId);
        } else {
            this.user.setNationalId(nationalId);
        }
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
    
    // ✅ GETTER for fertilizers (MISSING IN YOUR CODE)
    public Set<Fertilizer> getFertilizers() {
        return fertilizers;
    }
    
    // ✅ SETTER for fertilizers (MISSING IN YOUR CODE)
    public void setFertilizers(Set<Fertilizer> fertilizers) {
        this.fertilizers = fertilizers;
    }
    
    // ✅ HELPER METHOD to add fertilizer (MISSING IN YOUR CODE)
    public void addFertilizer(Fertilizer fertilizer) {
        this.fertilizers.add(fertilizer);
        fertilizer.getCrops().add(this);
    }
    
    // ✅ HELPER METHOD to remove fertilizer (OPTIONAL)
    public void removeFertilizer(Fertilizer fertilizer) {
        this.fertilizers.remove(fertilizer);
        fertilizer.getCrops().remove(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Crop crop = (Crop) obj;
        return cropId == crop.cropId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(cropId);
    }
    
    @Override
    public String toString() {
        return "Crop{" + 
                "cropId=" + cropId + 
                ", nationalId=" + (user != null ? user.getNationalId() : "null") + 
                ", cropName=" + cropName + 
                ", plantingDate=" + plantingDate + 
                ", harvestDate=" + harvestDate + 
                ", expectedYield=" + expectedYield + 
                ", status=" + status + 
                '}';
    }
}
