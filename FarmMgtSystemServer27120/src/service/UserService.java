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
import model.User;

public interface UserService extends Remote {
    // CRUD Operations
    public User saveUser(User theUser) throws RemoteException;
    public User updateUser(User theUser) throws RemoteException;
    public User deleteUser(User theUser) throws RemoteException;
    public User searchUserById(User theUser) throws RemoteException;
    public List<User> retrieveAllUsers() throws RemoteException;
    
    // ✅ RECOMMENDED: Flexible Login (email or phone or nationalId)
    public User login(String identifier, String password) throws RemoteException;
    
    // Alternative login methods
    public User loginByEmail(String email, String password) throws RemoteException;
    public User loginByPhone(String phoneNumber, String password) throws RemoteException;
    
    // Validation methods (for signup)
    public boolean userExists(String nationalId) throws RemoteException;
    public boolean emailExists(String email) throws RemoteException;
    public boolean phoneExists(String phoneNumber) throws RemoteException;
    
    // Find methods (for OTP/password reset)
    public User findByEmail(String email) throws RemoteException;
    public User findByPhone(String phoneNumber) throws RemoteException;
    public User findByNationalId(String nationalId) throws RemoteException;
    
    // Password management
    public boolean updatePassword(String identifier, String newPassword) throws RemoteException;
    
    // Search methods
    public List<User> findUsersByDistrict(String district) throws RemoteException;
    public List<User> searchUsersByName(String name) throws RemoteException;

    // OTP-based login and request
    public User loginWithOtp(String email, String otp) throws RemoteException;
    public boolean requestOtp(String email) throws RemoteException;
}