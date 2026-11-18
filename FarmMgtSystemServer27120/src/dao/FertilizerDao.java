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
import model.Fertilizer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FertilizerDao {
    
    // SAVE/CREATE
    public Fertilizer saveFertilizer(Fertilizer fertilizerObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(fertilizerObj);
            tr.commit();
            ss.close();
            return fertilizerObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public Fertilizer updateFertilizer(Fertilizer fertilizerObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(fertilizerObj);
            tr.commit();
            ss.close();
            return fertilizerObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public Fertilizer deleteFertilizer(Fertilizer fertilizerObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(fertilizerObj);
            tr.commit();
            ss.close();
            return fertilizerObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ID
    public Fertilizer findById(int fertilizerId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Fertilizer fertilizer = (Fertilizer) ss.get(Fertilizer.class, fertilizerId);
            ss.close();
            return fertilizer;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY NAME
    public Fertilizer findByName(String fertilizerName) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Fertilizer f WHERE f.fertilizerName = :name");
            query.setParameter("name", fertilizerName);
            Fertilizer fertilizer = (Fertilizer) query.uniqueResult();
            ss.close();
            return fertilizer;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL
    public List<Fertilizer> findAll() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Fertilizer> fertilizers = ss.createQuery("FROM Fertilizer ORDER BY fertilizerName").list();
            ss.close();
            return fertilizers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY TYPE
    public List<Fertilizer> findByType(String fertilizerType) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Fertilizer f WHERE f.fertilizerType = :type ORDER BY fertilizerName");
            query.setParameter("type", fertilizerType);
            List<Fertilizer> fertilizers = query.list();
            ss.close();
            return fertilizers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // SEARCH BY NAME
    public List<Fertilizer> searchByName(String name) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM Fertilizer f WHERE LOWER(f.fertilizerName) LIKE LOWER(:name) ORDER BY fertilizerName");
            query.setParameter("name", "%" + name + "%");
            List<Fertilizer> fertilizers = query.list();
            ss.close();
            return fertilizers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND FERTILIZERS FOR CROP (Many-to-Many)
    public List<Fertilizer> findFertilizersForCrop(int cropId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT f FROM Fertilizer f JOIN f.crops c WHERE c.cropId = :cropId ORDER BY f.fertilizerName");
            query.setParameter("cropId", cropId);
            List<Fertilizer> fertilizers = query.list();
            ss.close();
            return fertilizers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // CHECK IF NAME EXISTS
    public boolean nameExists(String fertilizerName) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(f) FROM Fertilizer f WHERE f.fertilizerName = :name");
            query.setParameter("name", fertilizerName);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null && count > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // COUNT ALL
    public int countAll() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(f) FROM Fertilizer f");
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null ? count.intValue() : 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
