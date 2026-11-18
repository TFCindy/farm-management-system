package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.User;
import service.UserService;

/**
 * Controller for User-related operations
 * Handles communication between View and RMI Service
 */
public class UserController {
    
    private UserService userService;
    
    public UserController() throws Exception {
        // Try lookup twice (helps transient network/registry hiccups)
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        Exception lastEx = null;
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                this.userService = (UserService) registry.lookup("UserService");
                return;
            } catch (Exception ex) {
                lastEx = ex;
                System.err.println("UserController lookup attempt " + attempt + " failed: " + ex.getMessage());
                if (attempt == 1) Thread.sleep(250);
            }
        }
        // If we reach here, both attempts failed — rethrow last exception
        throw lastEx;
    }
    
    /**
     * Login user with identifier (email/phone/nationalId) and password
     */
    public User login(String identifier, String password) throws RemoteException {
        return userService.login(identifier, password);
    }
    
    /**
     * Login user with email and password
     */
    public User loginByEmail(String email, String password) throws RemoteException {
        return userService.loginByEmail(email, password);
    }
    
    /**
     * Login user with phone and password
     */
    public User loginByPhone(String phoneNumber, String password) throws RemoteException {
        return userService.loginByPhone(phoneNumber, password);
    }
    
    /**
     * Save new user (signup)
     */
    public User saveUser(User user) throws RemoteException {
        return userService.saveUser(user);
    }
    
    /**
     * Update existing user
     */
    public User updateUser(User user) throws RemoteException {
        return userService.updateUser(user);
    }
    
    /**
     * Delete user
     */
    public User deleteUser(User user) throws RemoteException {
        return userService.deleteUser(user);
    }
    
    /**
     * Search user by ID
     */
    public User searchUserById(User user) throws RemoteException {
        return userService.searchUserById(user);
    }
    
    /**
     * Retrieve all users
     */
    public List<User> retrieveAllUsers() throws RemoteException {
        return userService.retrieveAllUsers();
    }
    
    /**
     * Check if user exists by national ID
     */
    public boolean userExists(String nationalId) throws RemoteException {
        return userService.userExists(nationalId);
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) throws RemoteException {
        return userService.emailExists(email);
    }
    
    /**
     * Check if phone exists
     */
    public boolean phoneExists(String phoneNumber) throws RemoteException {
        return userService.phoneExists(phoneNumber);
    }
    
    /**
     * Find user by email
     */
    public User findByEmail(String email) throws RemoteException {
        return userService.findByEmail(email);
    }
    
    /**
     * Find user by phone
     */
    public User findByPhone(String phoneNumber) throws RemoteException {
        return userService.findByPhone(phoneNumber);
    }
    
    /**
     * Find user by national ID
     */
    public User findByNationalId(String nationalId) throws RemoteException {
        return userService.findByNationalId(nationalId);
    }
    
    /**
     * Update password
     */
    public boolean updatePassword(String identifier, String newPassword) throws RemoteException {
        return userService.updatePassword(identifier, newPassword);
    }
    
    /**
     * Find users by district
     */
    public List<User> findUsersByDistrict(String district) throws RemoteException {
        return userService.findUsersByDistrict(district);
    }
    
    /**
     * Search users by name
     */
    public List<User> searchUsersByName(String name) throws RemoteException {
        return userService.searchUsersByName(name);
    }
    
    /**
     * Login with OTP
     */
    public User loginWithOtp(String email, String otp) throws RemoteException {
        return userService.loginWithOtp(email, otp);
    }
    
    /**
     * Request OTP
     */
    public boolean requestOtp(String email) throws RemoteException {
        return userService.requestOtp(email);
    }
}

