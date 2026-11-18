/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author PC
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Fertilizer;

public interface FertilizerService extends Remote {
    
    // CRUD Operations
    public Fertilizer saveFertilizer(Fertilizer theFertilizer) throws RemoteException;
    public Fertilizer updateFertilizer(Fertilizer theFertilizer) throws RemoteException;
    public Fertilizer deleteFertilizer(Fertilizer theFertilizer) throws RemoteException;
    public List<Fertilizer> getAllFertilizers() throws RemoteException;
    
    // Find operations
    public Fertilizer findById(int fertilizerId) throws RemoteException;
    public Fertilizer findByName(String fertilizerName) throws RemoteException;
    public List<Fertilizer> findByType(String fertilizerType) throws RemoteException;
    public List<Fertilizer> searchByName(String name) throws RemoteException;
    
    // Many-to-Many relationship
    public List<Fertilizer> getFertilizersForCrop(int cropId) throws RemoteException;
    
    // Validation
    public boolean nameExists(String fertilizerName) throws RemoteException;
    
    // Count
    public int countAllFertilizers() throws RemoteException;
}