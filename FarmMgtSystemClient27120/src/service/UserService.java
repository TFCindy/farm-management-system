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
    
    public User saveUser(User theUser) throws RemoteException;
    public User updateUser(User theUser) throws RemoteException;
    public User deleteUser(User theUser) throws RemoteException;
    public User searchUserById(User theUser) throws RemoteException;
    public List<User> retrieveAllUsers() throws RemoteException;

    // Flexible login (email/phone/nationalID)
    public User login(String identifier, String password) throws RemoteException;

    // Optional login methods
    public User loginByEmail(String email, String password) throws RemoteException;
    public User loginByPhone(String phoneNumber, String password) throws RemoteException;

    // Existence checks
    public boolean userExists(String nationalId) throws RemoteException;
    public boolean emailExists(String email) throws RemoteException;
    public boolean phoneExists(String phoneNumber) throws RemoteException;

    // Finders
    public User findByEmail(String email) throws RemoteException;
    public User findByPhone(String phoneNumber) throws RemoteException;
    public User findByNationalId(String nationalId) throws RemoteException;

    // Password update
    public boolean updatePassword(String identifier, String newPassword) throws RemoteException;

    // Search and filters
    public List<User> findUsersByDistrict(String district) throws RemoteException;
    public List<User> searchUsersByName(String name) throws RemoteException;

    // OTP-based login and request
    public User loginWithOtp(String email, String otp) throws RemoteException;
    public boolean requestOtp(String email) throws RemoteException;
}

