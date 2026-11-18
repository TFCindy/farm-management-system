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
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserDao {
    
    // SAVE/CREATE
    public User saveUser(User userObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(userObj);
            tr.commit();
            ss.close();
            return userObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // UPDATE
    public User updateUser(User userObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(userObj);
            tr.commit();
            ss.close();
            return userObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // DELETE
    public User deleteUser(User userObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(userObj);
            tr.commit();
            ss.close();
            return userObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY ID (using User object)
    public User findUserById(User userObj) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            User theUser = (User) ss.get(User.class, userObj.getNationalId());
            ss.close();
            return theUser;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND BY NATIONAL ID (String)
    public User findByNationalId(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            User user = (User) ss.get(User.class, nationalId);
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND ALL
    public List<User> findAllUsers() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<User> users = ss.createQuery("FROM User").list();
            ss.close();
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // ✅ RECOMMENDED: GENERIC LOGIN (email or phone or nationalId)
    public User login(String identifier, String password) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM User u WHERE (u.email = :identifier OR u.phoneNumber = :identifier OR u.nationalId = :identifier) AND u.password = :password";
            Query query = ss.createQuery(hql);
            query.setParameter("identifier", identifier);
            query.setParameter("password", password);
            User user = (User) query.uniqueResult();
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // LOGIN BY EMAIL ONLY
    public User loginByEmail(String email, String password) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User WHERE email = :email AND password = :pwd");
            query.setParameter("email", email);
            query.setParameter("pwd", password);
            User user = (User) query.uniqueResult();
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // LOGIN BY PHONE ONLY
    public User loginByPhone(String phoneNumber, String password) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User WHERE phoneNumber = :phone AND password = :pwd");
            query.setParameter("phone", phoneNumber);
            query.setParameter("pwd", password);
            User user = (User) query.uniqueResult();
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND USER BY EMAIL (for password reset)
    public User findByEmail(String email) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User WHERE email = :email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIND USER BY PHONE (for password reset)
    public User findByPhone(String phoneNumber) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User WHERE phoneNumber = :phone");
            query.setParameter("phone", phoneNumber);
            User user = (User) query.uniqueResult();
            ss.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // CHECK IF EMAIL EXISTS
    public boolean emailExists(String email) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null && count > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // CHECK IF PHONE EXISTS
    public boolean phoneExists(String phoneNumber) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :phone");
            query.setParameter("phone", phoneNumber);
            Long count = (Long) query.uniqueResult();
            ss.close();
            return count != null && count > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // CHECK IF NATIONAL ID EXISTS
    public boolean userExists(String nationalId) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            User user = (User) ss.get(User.class, nationalId);
            ss.close();
            return user != null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // UPDATE PASSWORD (for OTP reset)
    public boolean updatePassword(String identifier, String newPassword) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            
            String hql = "UPDATE User SET password = :pwd WHERE email = :identifier OR phoneNumber = :identifier OR nationalId = :identifier";
            Query query = ss.createQuery(hql);
            query.setParameter("pwd", newPassword);
            query.setParameter("identifier", identifier);
            
            int updated = query.executeUpdate();
            tr.commit();
            ss.close();
            
            return updated > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // FIND USERS BY DISTRICT
    public List<User> findUsersByDistrict(String district) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User u WHERE u.district = :district");
            query.setParameter("district", district);
            List<User> users = query.list();
            ss.close();
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // SEARCH USERS BY NAME
    public List<User> searchUsersByName(String name) {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Query query = ss.createQuery("FROM User u WHERE LOWER(u.firstname) LIKE LOWER(:name) OR LOWER(u.lastname) LIKE LOWER(:name)");
            query.setParameter("name", "%" + name + "%");
            List<User> users = query.list();
            ss.close();
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}