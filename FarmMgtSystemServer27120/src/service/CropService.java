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
import model.Crop;


public interface CropService extends Remote {
    public Crop saveCrop(Crop theCrop) throws RemoteException;
    public Crop updateCrop(Crop theCrop) throws RemoteException;
    public Crop deleteCrop(Crop theCrop) throws RemoteException;
    public Crop searchCropById(Crop theCrop) throws RemoteException;
    public List<Crop> retrieveAllCrops() throws RemoteException;
    public List<Crop> findCropsByUser(String nationalId) throws RemoteException;
    public List<Crop> searchCropsByName(String nationalId, String cropName) throws RemoteException;
    public List<Crop> findCropsByStatus(String nationalId, String status) throws RemoteException;
    public Long countCropsByUser(String nationalId) throws RemoteException;
    public Long countAllCrops() throws RemoteException;
    public Double getTotalExpectedYieldByUser(String nationalId) throws RemoteException;
}
