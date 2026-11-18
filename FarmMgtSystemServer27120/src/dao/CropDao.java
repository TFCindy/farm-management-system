/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author PC
 */

import java.util.List;
import model.Crop;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class CropDao {
    
    // SAVE/CREATE
    public Crop saveCrop(Crop cropObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // Entry log for diagnostics
            try { System.out.println("CropDao.saveCrop: received crop -> nid='" + cropObj.getNationalId() + "' name='" + cropObj.getCropName() + "' planting='" + cropObj.getPlantingDate() + "' harvest='" + cropObj.getHarvestDate() + "' yield='" + cropObj.getExpectedYield() + "' status='" + cropObj.getStatus() + "'"); } catch (Exception ignore) {}
            // Ensure the referenced User is managed (avoid transient instance errors)
            String nid = cropObj.getNationalId();
            if (nid == null) {
                // Try reflection to extract a raw 'nationalId' field in case
                // the incoming serialized object didn't populate the getter
                try {
                    java.lang.reflect.Field f = cropObj.getClass().getDeclaredField("nationalId");
                    f.setAccessible(true);
                    Object raw = f.get(cropObj);
                    if (raw != null) {
                        nid = raw.toString();
                        System.out.println("CropDao.saveCrop: reflection-found nationalId='" + nid + "'");
                        try { cropObj.setNationalId(nid); } catch (Exception ignore) {}
                    }
                } catch (NoSuchFieldException nsf) {
                    // Maybe the client sent a nested user object with the id—try that
                    try {
                        java.lang.reflect.Field uf = cropObj.getClass().getDeclaredField("user");
                        uf.setAccessible(true);
                        Object u = uf.get(cropObj);
                        if (u != null) {
                            try {
                                java.lang.reflect.Method gm = u.getClass().getMethod("getNationalId");
                                Object val = gm.invoke(u);
                                if (val != null) {
                                    nid = val.toString();
                                    System.out.println("CropDao.saveCrop: reflection-found nested user.nationalId='" + nid + "'");
                                    try { cropObj.setNationalId(nid); } catch (Exception ignore) {}
                                }
                            } catch (NoSuchMethodException nm) {
                                // try field access on user
                                try {
                                    java.lang.reflect.Field unf = u.getClass().getDeclaredField("nationalId");
                                    unf.setAccessible(true);
                                    Object uval = unf.get(u);
                                    if (uval != null) {
                                        nid = uval.toString();
                                        System.out.println("CropDao.saveCrop: reflection-found nested user.nationalId(field)='" + nid + "'");
                                        try { cropObj.setNationalId(nid); } catch (Exception ignore) {}
                                    }
                                } catch (Exception e) {
                                    // ignore further
                                }
                            }
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                } catch (Exception e) {
                    System.err.println("CropDao.saveCrop: reflection extraction error: " + e.getMessage());
                }
            }
            if (nid == null) {
                System.out.println("CropDao.saveCrop: missing nationalId on crop after reflection attempts");
                // Dump fields for diagnostics
                try {
                    java.lang.reflect.Field[] fields = cropObj.getClass().getDeclaredFields();
                    for (java.lang.reflect.Field f : fields) {
                        try {
                            f.setAccessible(true);
                            Object val = f.get(cropObj);
                            System.out.println("CropDao.saveCrop: field '" + f.getName() + "' -> '" + val + "'");
                        } catch (Exception fe) {
                            System.err.println("CropDao.saveCrop: could not read field '" + f.getName() + "': " + fe.getMessage());
                        }
                    }
                } catch (Exception fe) {
                    System.err.println("CropDao.saveCrop: reflection dump failed: " + fe.getMessage());
                }
                ss.close();
                return null;
            }
            model.User user = (model.User) ss.get(model.User.class, nid);
            if (user == null) {
                // Fallback: try case-insensitive / trimmed lookup
                String nn = nid == null ? null : nid.trim().toLowerCase();
                try {
                    Query uq = ss.createQuery("FROM User u WHERE LOWER(u.nationalId) = :nid");
                    uq.setParameter("nid", nn);
                    user = (model.User) uq.uniqueResult();
                } catch (Exception qex) {
                    // ignore
                }
            }
            if (user == null) {
                System.out.println("CropDao.saveCrop: user not found for nationalId='" + nid + "'");
                ss.close();
                return null;
            }
            cropObj.setUser(user);
            Transaction tr = ss.beginTransaction();
            ss.save(cropObj);
            tr.commit();
            ss.close();
            return cropObj;
        } catch (Exception ex) {
            System.err.println("CropDao.saveCrop: exception while saving crop: " + ex.getMessage());
            try { System.err.println("Crop data: " + cropObj); } catch (Exception ignore) {}
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public Crop updateCrop(Crop cropObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // Attach managed User before update
            String nid = cropObj.getNationalId();
            if (nid == null) {
                System.out.println("CropDao.updateCrop: missing nationalId on crop");
                ss.close();
                return null;
            }
            model.User user = (model.User) ss.get(model.User.class, nid);
            if (user == null) {
                String nn = nid == null ? null : nid.trim().toLowerCase();
                try {
                    Query uq = ss.createQuery("FROM User u WHERE LOWER(u.nationalId) = :nid");
                    uq.setParameter("nid", nn);
                    user = (model.User) uq.uniqueResult();
                } catch (Exception qex) {
                }
            }
            if (user == null) {
                System.out.println("CropDao.updateCrop: user not found for nationalId='" + nid + "'");
                ss.close();
                return null;
            }
            cropObj.setUser(user);
            Transaction tr = ss.beginTransaction();
            ss.update(cropObj);
            tr.commit();
            ss.close();
            return cropObj;
        } catch (Exception ex) {
            System.err.println("CropDao.updateCrop: exception while updating crop: " + ex.getMessage());
            try { System.err.println("Crop data: " + cropObj); } catch (Exception ignore) {}
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public Crop deleteCrop(Crop cropObj) {
        try {
            System.out.println("CropDao.deleteCrop: Attempting to delete crop with ID: " + cropObj.getCropId());
            
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            
            // First fetch the entity to ensure it's attached to the session
            Crop managedCrop = (Crop) ss.get(Crop.class, cropObj.getCropId());
            if (managedCrop != null) {
                System.out.println("CropDao.deleteCrop: Found crop to delete: " + managedCrop);
                
                // Clear all relationships to avoid foreign key constraint violations
                if (managedCrop.getFertilizers() != null && !managedCrop.getFertilizers().isEmpty()) {
                    System.out.println("CropDao.deleteCrop: Clearing " + managedCrop.getFertilizers().size() + " fertilizer relationships");
                    managedCrop.getFertilizers().clear();
                }
                
                ss.delete(managedCrop);
                tr.commit();
                ss.close();
                System.out.println("CropDao.deleteCrop: Successfully deleted crop with ID: " + cropObj.getCropId());
                return managedCrop;
            } else {
                System.out.println("CropDao.deleteCrop: Crop not found with ID: " + cropObj.getCropId());
                tr.rollback();
                ss.close();
                return null;
            }
        } catch (Exception ex) {
            System.err.println("CropDao.deleteCrop: Exception occurred: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ID (using Crop object)
    public Crop findCropById(Crop cropObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Crop theCrop = (Crop) ss.get(Crop.class, cropObj.getCropId());
            ss.close();
            return theCrop;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL
    public List<Crop> findAllCrops() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Crop> crops = ss.createQuery("FROM Crop").list();
            ss.close();
            return crops;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY NATIONAL ID (get crops for specific user)
    public List<Crop> findCropsByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Crop c WHERE c.user.nationalId = :nid ORDER BY c.cropId DESC");
            query.setParameter("nid", nationalId);
            List<Crop> crops = query.list();
            ss.close();
            return crops;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // SEARCH BY CROP NAME for specific user
    public List<Crop> searchCropsByName(String nationalId, String cropName) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Crop c WHERE c.user.nationalId = :nid AND LOWER(c.cropName) LIKE LOWER(:cname) ORDER BY c.cropId DESC");
            query.setParameter("nid", nationalId);
            query.setParameter("cname", "%" + cropName + "%");
            List<Crop> crops = query.list();
            ss.close();
            return crops;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY CROP ID for specific user
    public Crop findCropByIdAndUser(int cropId, String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Crop c WHERE c.cropId = :cid AND c.user.nationalId = :nid");
            query.setParameter("cid", cropId);
            query.setParameter("nid", nationalId);
            Crop crop = (Crop) query.uniqueResult();
            ss.close();
            return crop;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // COUNT crops for specific user
    public Long countCropsByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(c) FROM Crop c WHERE c.user.nationalId = :nid");
            query.setParameter("nid", nationalId);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // COUNT ALL crops
    public Long countAllCrops() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(c) FROM Crop c");
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // FIND RECENT crops for user
    public List<Crop> findRecentCropsByUser(String nationalId, int limit) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Crop c WHERE c.user.nationalId = :nid ORDER BY c.cropId DESC");
            query.setParameter("nid", nationalId);
            query.setMaxResults(limit);
            List<Crop> crops = query.list();
            ss.close();
            return crops;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY STATUS for specific user
    public List<Crop> findCropsByStatus(String nationalId, String status) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Crop c WHERE c.user.nationalId = :nid AND c.status = :status ORDER BY c.cropId DESC");
            query.setParameter("nid", nationalId);
            query.setParameter("status", status);
            List<Crop> crops = query.list();
            ss.close();
            return crops;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // GET TOTAL EXPECTED YIELD for specific user
    public Double getTotalExpectedYieldByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT SUM(c.expectedYield) FROM Crop c WHERE c.user.nationalId = :nid");
            query.setParameter("nid", nationalId);
            Double total = (Double) query.uniqueResult();
            ss.close();
            return total != null ? total : 0.0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }
}