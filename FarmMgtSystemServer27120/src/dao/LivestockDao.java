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
import model.Livestock;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class LivestockDao {
    
    // SAVE/CREATE
    public Livestock saveLivestock(Livestock livestockObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(livestockObj);
            tr.commit();
            ss.close();
            return livestockObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public Livestock updateLivestock(Livestock livestockObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(livestockObj);
            tr.commit();
            ss.close();
            return livestockObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public Livestock deleteLivestock(Livestock livestockObj) {
        try {
            System.out.println("LivestockDao.deleteLivestock: Attempting to delete livestock with ID: " + livestockObj.getAnimalId());
            
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            
            // First fetch the entity to ensure it's attached to the session
            Livestock managedLivestock = (Livestock) ss.get(Livestock.class, livestockObj.getAnimalId());
            if (managedLivestock != null) {
                System.out.println("LivestockDao.deleteLivestock: Found livestock to delete: " + managedLivestock);
                ss.delete(managedLivestock);
                tr.commit();
                ss.close();
                System.out.println("LivestockDao.deleteLivestock: Successfully deleted livestock with ID: " + livestockObj.getAnimalId());
                return managedLivestock;
            } else {
                System.out.println("LivestockDao.deleteLivestock: Livestock not found with ID: " + livestockObj.getAnimalId());
                tr.rollback();
                ss.close();
                return null;
            }
        } catch (Exception ex) {
            System.err.println("LivestockDao.deleteLivestock: Exception occurred: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ID (using Livestock object)
    public Livestock findLivestockById(Livestock livestockObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Livestock theLivestock = (Livestock) ss.get(Livestock.class, livestockObj.getAnimalId());
            ss.close();
            return theLivestock;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL
    public List<Livestock> findAllLivestock() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Livestock> livestockList = ss.createQuery("FROM Livestock").list();
            ss.close();
            return livestockList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY NATIONAL ID (get livestock for specific user)
    public List<Livestock> findLivestockByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Livestock l WHERE l.user.nationalId = :nid ORDER BY l.animalId DESC");
            query.setParameter("nid", nationalId);
            List<Livestock> livestockList = query.list();
            ss.close();
            return livestockList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // SEARCH BY ANIMAL TYPE for specific user
    public List<Livestock> searchLivestockByAnimalType(String nationalId, String animalType) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Livestock l WHERE l.user.nationalId = :nid AND LOWER(l.animalType) LIKE LOWER(:atype) ORDER BY l.animalId DESC");
            query.setParameter("nid", nationalId);
            query.setParameter("atype", "%" + animalType + "%");
            List<Livestock> livestockList = query.list();
            ss.close();
            return livestockList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ANIMAL ID for specific user
    public Livestock findLivestockByIdAndUser(int animalId, String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Livestock l WHERE l.animalId = :aid AND l.user.nationalId = :nid");
            query.setParameter("aid", animalId);
            query.setParameter("nid", nationalId);
            Livestock livestock = (Livestock) query.uniqueResult();
            ss.close();
            return livestock;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // COUNT livestock for specific user
    public Long countLivestockByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(l) FROM Livestock l WHERE l.user.nationalId = :nid");
            query.setParameter("nid", nationalId);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // COUNT ALL livestock
    public Long countAllLivestock() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(l) FROM Livestock l");
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count : 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
    // COUNT TOTAL QUANTITY for specific user
    public Integer countTotalQuantityByUser(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT SUM(l.quantity) FROM Livestock l WHERE l.user.nationalId = :nid");
            query.setParameter("nid", nationalId);
            Long total = (Long) query.uniqueResult();
            ss.close();
            return total != null ? total.intValue() : 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    // COUNT TOTAL QUANTITY (all users)
    public Integer countTotalQuantity() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT SUM(l.quantity) FROM Livestock l");
            Long total = (Long) query.uniqueResult();
            ss.close();
            return total != null ? total.intValue() : 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    // FIND RECENT livestock for user
    public List<Livestock> findRecentLivestockByUser(String nationalId, int limit) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Livestock l WHERE l.user.nationalId = :nid ORDER BY l.animalId DESC");
            query.setParameter("nid", nationalId);
            query.setMaxResults(limit);
            List<Livestock> livestockList = query.list();
            ss.close();
            return livestockList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY HEALTH STATUS for specific user
    public List<Livestock> findLivestockByHealthStatus(String nationalId, String healthStatus) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Livestock l WHERE l.user.nationalId = :nid AND l.healthStatus = :hstatus ORDER BY l.animalId DESC");
            query.setParameter("nid", nationalId);
            query.setParameter("hstatus", healthStatus);
            List<Livestock> livestockList = query.list();
            ss.close();
            return livestockList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // GROUP BY ANIMAL TYPE for specific user
    public List<Object[]> groupLivestockByType(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT l.animalType, COUNT(l), SUM(l.quantity) FROM Livestock l WHERE l.user.nationalId = :nid GROUP BY l.animalType");
            query.setParameter("nid", nationalId);
            List<Object[]> results = query.list();
            ss.close();
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
