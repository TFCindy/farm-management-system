package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.Fertilizer;
import service.FertilizerService;

/**
 * Controller for Fertilizer-related operations
 * Handles communication between View and RMI Service
 */
public class FertilizerController {
    
    private FertilizerService fertilizerService;
    
    public FertilizerController() throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        this.fertilizerService = (FertilizerService) registry.lookup("FertilizerService");
    }
    
    public Fertilizer saveFertilizer(Fertilizer fertilizer) throws RemoteException {
        return fertilizerService.saveFertilizer(fertilizer);
    }
    
    public Fertilizer updateFertilizer(Fertilizer fertilizer) throws RemoteException {
        return fertilizerService.updateFertilizer(fertilizer);
    }
    
    public Fertilizer deleteFertilizer(Fertilizer fertilizer) throws RemoteException {
        return fertilizerService.deleteFertilizer(fertilizer);
    }
    
    public List<Fertilizer> getAllFertilizers() throws RemoteException {
        return fertilizerService.getAllFertilizers();
    }
    
    public Fertilizer findById(int fertilizerId) throws RemoteException {
        return fertilizerService.findById(fertilizerId);
    }
    
    public Fertilizer findByName(String fertilizerName) throws RemoteException {
        return fertilizerService.findByName(fertilizerName);
    }
    
    public List<Fertilizer> findByType(String fertilizerType) throws RemoteException {
        return fertilizerService.findByType(fertilizerType);
    }
    
    public List<Fertilizer> searchByName(String name) throws RemoteException {
        return fertilizerService.searchByName(name);
    }
    
    public List<Fertilizer> getFertilizersForCrop(int cropId) throws RemoteException {
        return fertilizerService.getFertilizersForCrop(cropId);
    }
    
    public boolean nameExists(String fertilizerName) throws RemoteException {
        return fertilizerService.nameExists(fertilizerName);
    }
    
    public int countAllFertilizers() throws RemoteException {
        return fertilizerService.countAllFertilizers();
    }
}

