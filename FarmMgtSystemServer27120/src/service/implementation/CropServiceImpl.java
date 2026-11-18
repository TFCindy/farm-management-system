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
import model.Crop;
import dao.CropDao;
import service.CropService;


public class CropServiceImpl extends UnicastRemoteObject implements CropService {
    
    private CropDao cropDao;
    
    public CropServiceImpl() throws RemoteException {
        super();
        cropDao = new CropDao();
    }
    
    @Override
    public Crop saveCrop(Crop theCrop) throws RemoteException {
        try {
            System.out.println("CropServiceImpl.saveCrop: request received for nationalId='" + (theCrop != null ? theCrop.getNationalId() : "<null>") + "'");
            // Additional diagnostics: dump incoming object fields to help identify
            // serialization mismatches or missing values coming from the client.
            if (theCrop != null) {
                try {
                    System.out.println("CropServiceImpl.saveCrop: incoming object class=" + theCrop.getClass().getName());
                } catch (Exception ignore) {}
                try { System.out.println("CropServiceImpl.saveCrop: incoming toString: " + theCrop); } catch (Exception ignore) {}
                try {
                    String nid = null;
                    try { nid = theCrop.getNationalId(); } catch (Exception ge) { System.err.println("CropServiceImpl.saveCrop: getNationalId() threw: " + ge.getMessage()); }
                    System.out.println("CropServiceImpl.saveCrop: getNationalId() -> '" + nid + "'");
                    java.lang.reflect.Field[] fields = theCrop.getClass().getDeclaredFields();
                    for (java.lang.reflect.Field f : fields) {
                        try {
                            f.setAccessible(true);
                            Object val = f.get(theCrop);
                            System.out.println("CropServiceImpl.saveCrop: field '" + f.getName() + "' -> '" + val + "'");
                        } catch (Exception fe) {
                            System.err.println("CropServiceImpl.saveCrop: could not read field '" + f.getName() + "': " + fe.getMessage());
                        }
                    }
                } catch (Exception re) {
                    System.err.println("CropServiceImpl.saveCrop: reflection dump failed: " + re.getMessage());
                }
            }
            // If theCrop doesn't expose a nationalId via getter, try reflection
            if (theCrop != null && (theCrop.getNationalId() == null || theCrop.getNationalId().trim().isEmpty())) {
                try {
                    java.lang.reflect.Field f = theCrop.getClass().getDeclaredField("nationalId");
                    f.setAccessible(true);
                    Object raw = f.get(theCrop);
                    if (raw != null) {
                        String nid = raw.toString();
                        try { theCrop.setNationalId(nid); } catch (Exception ignore) {}
                        System.out.println("CropServiceImpl.saveCrop: reflection-set nationalId='" + nid + "'");
                    }
                } catch (NoSuchFieldException nsf) {
                    // try nested user object
                    try {
                        java.lang.reflect.Field uf = theCrop.getClass().getDeclaredField("user");
                        uf.setAccessible(true);
                        Object u = uf.get(theCrop);
                        if (u != null) {
                            try {
                                java.lang.reflect.Method gm = u.getClass().getMethod("getNationalId");
                                Object val = gm.invoke(u);
                                if (val != null) {
                                    String nid = val.toString();
                                    try { theCrop.setNationalId(nid); } catch (Exception ignore) {}
                                    System.out.println("CropServiceImpl.saveCrop: reflection-set nested user.nationalId='" + nid + "'");
                                }
                            } catch (NoSuchMethodException nm) {
                                try {
                                    java.lang.reflect.Field unf = u.getClass().getDeclaredField("nationalId");
                                    unf.setAccessible(true);
                                    Object uval = unf.get(u);
                                    if (uval != null) {
                                        String nid = uval.toString();
                                        try { theCrop.setNationalId(nid); } catch (Exception ignore) {}
                                        System.out.println("CropServiceImpl.saveCrop: reflection-set nested user.nationalId(field)='" + nid + "'");
                                    }
                                } catch (Exception e) {
                                    // ignore
                                }
                            }
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                } catch (Exception e) {
                    System.err.println("CropServiceImpl.saveCrop: reflection extraction error: " + e.getMessage());
                }
            }

            Crop saved = cropDao.saveCrop(theCrop);
            if (saved == null) {
                String nid = theCrop != null ? theCrop.getNationalId() : "<null>";
                String msg = "Failed to save crop. Possible cause: user not found for nationalId='" + nid + "' or database error. Check server logs for details.";
                System.err.println("CropServiceImpl.saveCrop: " + msg);
                throw new RemoteException(msg);
            }
            System.out.println("CropServiceImpl.saveCrop: saved crop id=" + saved.getCropId());
            return saved;
        } catch (RemoteException re) {
            throw re;
        } catch (Exception e) {
            System.err.println("CropServiceImpl.saveCrop: unexpected exception: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Error saving crop: " + e.getMessage());
        }
    }
    
    @Override
    public Crop updateCrop(Crop theCrop) throws RemoteException {
        try {
            return cropDao.updateCrop(theCrop);
        } catch (Exception e) {
            throw new RemoteException("Error updating crop: " + e.getMessage());
        }
    }
    
    @Override
    public Crop deleteCrop(Crop theCrop) throws RemoteException {
        try {
            Crop deletedCrop = cropDao.deleteCrop(theCrop);
            if (deletedCrop == null) {
                throw new RemoteException("Failed to delete crop. Record may not exist or you don't have permission.");
            }
            return deletedCrop;
        } catch (RemoteException re) {
            throw re;
        } catch (Exception e) {
            throw new RemoteException("Error deleting crop: " + e.getMessage());
        }
    }
    
    @Override
    public Crop searchCropById(Crop theCrop) throws RemoteException {
        try {
            return cropDao.findCropById(theCrop);
        } catch (Exception e) {
            throw new RemoteException("Error finding crop: " + e.getMessage());
        }
    }
    
    @Override
    public List<Crop> retrieveAllCrops() throws RemoteException {
        try {
            return cropDao.findAllCrops();
        } catch (Exception e) {
            throw new RemoteException("Error retrieving crops: " + e.getMessage());
        }
    }
    
    @Override
    public List<Crop> findCropsByUser(String nationalId) throws RemoteException {
        try {
            return cropDao.findCropsByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error finding crops for user: " + e.getMessage());
        }
    }
    
    @Override
    public List<Crop> searchCropsByName(String nationalId, String cropName) throws RemoteException {
        try {
            return cropDao.searchCropsByName(nationalId, cropName);
        } catch (Exception e) {
            throw new RemoteException("Error searching crops: " + e.getMessage());
        }
    }
    
    @Override
    public List<Crop> findCropsByStatus(String nationalId, String status) throws RemoteException {
        try {
            return cropDao.findCropsByStatus(nationalId, status);
        } catch (Exception e) {
            throw new RemoteException("Error finding crops by status: " + e.getMessage());
        }
    }
    
    @Override
    public Long countCropsByUser(String nationalId) throws RemoteException {
        try {
            return cropDao.countCropsByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error counting crops: " + e.getMessage());
        }
    }
    
    @Override
    public Long countAllCrops() throws RemoteException {
        try {
            return cropDao.countAllCrops();
        } catch (Exception e) {
            throw new RemoteException("Error counting all crops: " + e.getMessage());
        }
    }
    
    @Override
    public Double getTotalExpectedYieldByUser(String nationalId) throws RemoteException {
        try {
            return cropDao.getTotalExpectedYieldByUser(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error calculating total yield: " + e.getMessage());
        }
    }
}
