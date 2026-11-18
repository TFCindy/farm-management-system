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
import model.Livestock;
import dao.LivestockDao;
import service.LivestockService;


public class LivestockServiceImpl extends UnicastRemoteObject implements LivestockService {
    
    private LivestockDao livestockDao;
    
    public LivestockServiceImpl() throws RemoteException {
        super();
        livestockDao = new LivestockDao();
    }
    
    @Override
    public Livestock saveLivestock(Livestock theLivestock) throws RemoteException {
        try {
            return livestockDao.saveLivestock(theLivestock);
        } catch (Exception e) {
            throw new RemoteException("Error saving livestock: " + e.getMessage());
        }
    }
    
    @Override
    public Livestock updateLivestock(Livestock theLivestock) throws RemoteException {
        try {
            return livestockDao.updateLivestock(theLivestock);
        } catch (Exception e) {
            throw new RemoteException("Error updating livestock: " + e.getMessage());
        }
    }
    
    @Override
    public Livestock deleteLivestock(Livestock theLivestock) throws RemoteException {
        try {
            Livestock deletedLivestock = livestockDao.deleteLivestock(theLivestock);
            if (deletedLivestock == null) {
                throw new RemoteException("Failed to delete livestock. Record may not exist or you don't have permission.");
            }
            return deletedLivestock;
        } catch (RemoteException re) {
            throw re;
        } catch (Exception e) {
            throw new RemoteException("Error deleting livestock: " + e.getMessage());
        }
    }
    
    @Override
    public Livestock searchLivestockById(Livestock theLivestock) throws RemoteException {
        try {
            return livestockDao.findLivestockById(theLivestock);
        } catch (Exception e) {
            throw new RemoteException("Error finding livestock: " + e.getMessage());
        }
    }
    
    @Override
    public List<Livestock> retrieveAllLivestock() throws RemoteException {
        try {
            return livestockDao.findAllLivestock();
        } catch (Exception e) {
            throw new RemoteException("Error retrieving livestock: " + e.getMessage());
        }
    }
    
    @Override
    public List<Livestock> findLivestockByUser(String nationalId) throws RemoteException {
        try {
            return livestockDao.findLivestockByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error finding livestock for user: " + e.getMessage());
        }
    }
    
    @Override
    public List<Livestock> searchLivestockByAnimalType(String nationalId, String animalType) throws RemoteException {
        try {
            return livestockDao.searchLivestockByAnimalType(nationalId, animalType);
        } catch (Exception e) {
            throw new RemoteException("Error searching livestock: " + e.getMessage());
        }
    }
    
    @Override
    public List<Livestock> findLivestockByHealthStatus(String nationalId, String healthStatus) throws RemoteException {
        try {
            return livestockDao.findLivestockByHealthStatus(nationalId, healthStatus);
        } catch (Exception e) {
            throw new RemoteException("Error finding livestock by health status: " + e.getMessage());
        }
    }
    
    @Override
    public Long countLivestockByUser(String nationalId) throws RemoteException {
        try {
            return livestockDao.countLivestockByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error counting livestock: " + e.getMessage());
        }
    }
    
    @Override
    public Long countAllLivestock() throws RemoteException {
        try {
            return livestockDao.countAllLivestock();
        } catch (Exception e) {
            throw new RemoteException("Error counting all livestock: " + e.getMessage());
        }
    }
    
    @Override
    public Integer countTotalQuantityByUser(String nationalId) throws RemoteException {
        try {
            return livestockDao.countTotalQuantityByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error calculating total quantity: " + e.getMessage());
        }
    }
    
    @Override
    public Integer countTotalQuantity() throws RemoteException {
        try {
            return livestockDao.countTotalQuantity();
        } catch (Exception e) {
            throw new RemoteException("Error calculating total quantity for all: " + e.getMessage());
        }
    }
    
    @Override
    public List<Object[]> groupLivestockByType(String nationalId) throws RemoteException {
        try {
            return livestockDao.groupLivestockByType(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error grouping livestock by type: " + e.getMessage());
        }
    }
}