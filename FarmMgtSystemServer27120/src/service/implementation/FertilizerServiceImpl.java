/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.implementation;

/**
 *
 * @author PC
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Fertilizer;
import dao.FertilizerDao;
import service.FertilizerService;

public class FertilizerServiceImpl extends UnicastRemoteObject implements FertilizerService {
    
    private FertilizerDao fertilizerDao;
    
    public FertilizerServiceImpl() throws RemoteException {
        super();
        fertilizerDao = new FertilizerDao();
    }
    
    @Override
    public Fertilizer saveFertilizer(Fertilizer theFertilizer) throws RemoteException {
        try {
            return fertilizerDao.saveFertilizer(theFertilizer);
        } catch (Exception e) {
            throw new RemoteException("Error saving fertilizer: " + e.getMessage());
        }
    }
    
    @Override
    public Fertilizer updateFertilizer(Fertilizer theFertilizer) throws RemoteException {
        try {
            return fertilizerDao.updateFertilizer(theFertilizer);
        } catch (Exception e) {
            throw new RemoteException("Error updating fertilizer: " + e.getMessage());
        }
    }
    
    @Override
    public Fertilizer deleteFertilizer(Fertilizer theFertilizer) throws RemoteException {
        try {
            return fertilizerDao.deleteFertilizer(theFertilizer);
        } catch (Exception e) {
            throw new RemoteException("Error deleting fertilizer: " + e.getMessage());
        }
    }
    
    @Override
    public List<Fertilizer> getAllFertilizers() throws RemoteException {
        try {
            return fertilizerDao.findAll();
        } catch (Exception e) {
            throw new RemoteException("Error getting fertilizers: " + e.getMessage());
        }
    }
    
    @Override
    public Fertilizer findById(int fertilizerId) throws RemoteException {
        try {
            return fertilizerDao.findById(fertilizerId);
        } catch (Exception e) {
            throw new RemoteException("Error finding fertilizer: " + e.getMessage());
        }
    }
    
    @Override
    public Fertilizer findByName(String fertilizerName) throws RemoteException {
        try {
            return fertilizerDao.findByName(fertilizerName);
        } catch (Exception e) {
            throw new RemoteException("Error finding fertilizer by name: " + e.getMessage());
        }
    }
    
    @Override
    public List<Fertilizer> findByType(String fertilizerType) throws RemoteException {
        try {
            return fertilizerDao.findByType(fertilizerType);
        } catch (Exception e) {
            throw new RemoteException("Error finding fertilizers by type: " + e.getMessage());
        }
    }
    
    @Override
    public List<Fertilizer> searchByName(String name) throws RemoteException {
        try {
            return fertilizerDao.searchByName(name);
        } catch (Exception e) {
            throw new RemoteException("Error searching fertilizers: " + e.getMessage());
        }
    }
    
    @Override
    public List<Fertilizer> getFertilizersForCrop(int cropId) throws RemoteException {
        try {
            return fertilizerDao.findFertilizersForCrop(cropId);
        } catch (Exception e) {
            throw new RemoteException("Error getting fertilizers for crop: " + e.getMessage());
        }
    }
    
    @Override
    public boolean nameExists(String fertilizerName) throws RemoteException {
        try {
            return fertilizerDao.nameExists(fertilizerName);
        } catch (Exception e) {
            throw new RemoteException("Error checking fertilizer name: " + e.getMessage());
        }
    }
    
    @Override
    public int countAllFertilizers() throws RemoteException {
        try {
            return fertilizerDao.countAll();
        } catch (Exception e) {
            throw new RemoteException("Error counting fertilizers: " + e.getMessage());
        }
    }
}