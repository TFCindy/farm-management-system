package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.Livestock;
import model.User;
import service.LivestockService;

/**
 * Controller for Livestock-related operations
 * Handles communication between View and RMI Service
 */
public class LivestockController {
    
    private LivestockService livestockService;
    
    public LivestockController() throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        this.livestockService = (LivestockService) registry.lookup("LivestockService");
    }
    
    /**
     * Save new livestock
     */
    public Livestock saveLivestock(Livestock livestock) throws RemoteException {
        return livestockService.saveLivestock(livestock);
    }
    
    /**
     * Update existing livestock
     */
    public Livestock updateLivestock(Livestock livestock) throws RemoteException {
        return livestockService.updateLivestock(livestock);
    }
    
    /**
     * Delete livestock
     */
    public Livestock deleteLivestock(Livestock livestock) throws RemoteException {
        return livestockService.deleteLivestock(livestock);
    }
    
    /**
     * Search livestock by ID
     */
    public Livestock searchLivestockById(Livestock livestock) throws RemoteException {
        return livestockService.searchLivestockById(livestock);
    }
    
    /**
     * Retrieve all livestock
     */
    public List<Livestock> retrieveAllLivestock() throws RemoteException {
        return livestockService.retrieveAllLivestock();
    }
    
    /**
     * Find livestock by user (nationalId)
     */
    public List<Livestock> findLivestockByUser(String nationalId) throws RemoteException {
        return livestockService.findLivestockByUser(nationalId);
    }
    
    /**
     * Search livestock by animal type
     */
    public List<Livestock> searchLivestockByAnimalType(String nationalId, String animalType) throws RemoteException {
        return livestockService.searchLivestockByAnimalType(nationalId, animalType);
    }
    
    /**
     * Find livestock by health status
     */
    public List<Livestock> findLivestockByHealthStatus(String nationalId, String healthStatus) throws RemoteException {
        return livestockService.findLivestockByHealthStatus(nationalId, healthStatus);
    }
    
    /**
     * Count livestock by user
     */
    public Long countLivestockByUser(String nationalId) throws RemoteException {
        return livestockService.countLivestockByUser(nationalId);
    }
    
    /**
     * Count all livestock
     */
    public Long countAllLivestock() throws RemoteException {
        return livestockService.countAllLivestock();
    }
    
    /**
     * Count total quantity by user
     */
    public Integer countTotalQuantityByUser(String nationalId) throws RemoteException {
        return livestockService.countTotalQuantityByUser(nationalId);
    }
    
    /**
     * Count total quantity (all users)
     */
    public Integer countTotalQuantity() throws RemoteException {
        return livestockService.countTotalQuantity();
    }
    
    /**
     * Group livestock by type
     */
    public List<Object[]> groupLivestockByType(String nationalId) throws RemoteException {
        return livestockService.groupLivestockByType(nationalId);
    }
    
    /**
     * Helper method: Delete livestock by ID and nationalId
     * Creates a Livestock object with the ID and calls deleteLivestock
     */
    public boolean deleteLivestockById(int animalId, String nationalId) throws RemoteException {
        Livestock livestock = new Livestock();
        livestock.setAnimalId(animalId);
        // Livestock uses User object, not nationalId directly
        User user = new User();
        user.setNationalId(nationalId);
        livestock.setUser(user);
        Livestock deleted = deleteLivestock(livestock);
        return deleted != null;
    }
    
    /**
     * Helper method: Create livestock (for compatibility with views expecting int return)
     */
    public int createLivestock(Livestock livestock) throws RemoteException {
        Livestock saved = saveLivestock(livestock);
        return saved != null ? 1 : 0;
    }
    
    /**
     * Helper method: Update livestock (for compatibility with views expecting int return)
     */
    public int updateLivestockById(Livestock livestock) throws RemoteException {
        Livestock updated = updateLivestock(livestock);
        return updated != null ? 1 : 0;
    }
}

