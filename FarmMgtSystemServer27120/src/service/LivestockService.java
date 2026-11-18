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
import model.Livestock;


public interface LivestockService extends Remote {
    public Livestock saveLivestock(Livestock theLivestock) throws RemoteException;
    public Livestock updateLivestock(Livestock theLivestock) throws RemoteException;
    public Livestock deleteLivestock(Livestock theLivestock) throws RemoteException;
    public Livestock searchLivestockById(Livestock theLivestock) throws RemoteException;
    public List<Livestock> retrieveAllLivestock() throws RemoteException;
    public List<Livestock> findLivestockByUser(String nationalId) throws RemoteException;
    public List<Livestock> searchLivestockByAnimalType(String nationalId, String animalType) throws RemoteException;
    public List<Livestock> findLivestockByHealthStatus(String nationalId, String healthStatus) throws RemoteException;
    public Long countLivestockByUser(String nationalId) throws RemoteException;
    public Long countAllLivestock() throws RemoteException;
    public Integer countTotalQuantityByUser(String nationalId) throws RemoteException;
    public Integer countTotalQuantity() throws RemoteException;
    public List<Object[]> groupLivestockByType(String nationalId) throws RemoteException;
}
