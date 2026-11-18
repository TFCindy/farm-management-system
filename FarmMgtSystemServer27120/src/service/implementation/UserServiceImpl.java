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
import model.User;
import dao.UserDao;
import notification.OtpService;
import notification.RabbitMqPublisher;
import service.UserService;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    
    private UserDao userDao;
    // Message broker publisher used to publish OTP notifications
    private RabbitMqPublisher mqPublisher;
    
    public UserServiceImpl() throws RemoteException {
        super();
        userDao = new UserDao();
        // Configure RabbitMQ publisher (defaults)
        try {
            mqPublisher = new RabbitMqPublisher("localhost", 5672, "guest", "guest");
        } catch (Exception ex) {
            System.err.println("Failed to initialize RabbitMQ publisher: " + ex.getMessage());
            ex.printStackTrace();
            mqPublisher = null;
        }
    }
    
    @Override
    public User saveUser(User theUser) throws RemoteException {
        try {
            User u = userDao.saveUser(theUser);
            // Immediately request and publish OTP via message broker
            if (u != null && u.getEmail() != null && !u.getEmail().isEmpty()) {
                String otp = OtpService.getInstance().generateOtp(u.getEmail());
                boolean mqOk = false;
                if (mqPublisher != null) {
                    mqOk = mqPublisher.publishOtp(u.getEmail(), otp);
                    if (!mqOk) System.err.println("Warning: OTP publish failed for " + u.getEmail());
                } else {
                    System.err.println("No MQ publisher configured — OTP not pushed to broker.");
                }
                // Always attempt to send email directly (fallback or primary)
                System.out.println("Attempting to send OTP email to " + u.getEmail() + "...");
                boolean emailSent = notification.EmailSender.sendOtp(u.getEmail(), otp);
                if (emailSent) {
                    System.out.println("OTP email sent successfully to " + u.getEmail());
                } else {
                    System.err.println("Warning: OTP email failed to send to " + u.getEmail() + ". Please check SMTP configuration.");
                }
            }
            return u;
        } catch (Exception e) {
            throw new RemoteException("Error saving user: " + e.getMessage());
        }
    }
    
    @Override
    public User updateUser(User theUser) throws RemoteException {
        try {
            return userDao.updateUser(theUser);
        } catch (Exception e) {
            throw new RemoteException("Error updating user: " + e.getMessage());
        }
    }
    
    @Override
    public User deleteUser(User theUser) throws RemoteException {
        try {
            return userDao.deleteUser(theUser);
        } catch (Exception e) {
            throw new RemoteException("Error deleting user: " + e.getMessage());
        }
    }
    
    @Override
    public User searchUserById(User theUser) throws RemoteException {
        try {
            return userDao.findUserById(theUser);
        } catch (Exception e) {
            throw new RemoteException("Error finding user: " + e.getMessage());
        }
    }
    
    @Override
    public List<User> retrieveAllUsers() throws RemoteException {
        try {
            return userDao.findAllUsers();
        } catch (Exception e) {
            throw new RemoteException("Error retrieving users: " + e.getMessage());
        }
    }
    
    // ✅ RECOMMENDED: Use this flexible login method
    @Override
    public User login(String identifier, String password) throws RemoteException {
        try {
            User user = userDao.login(identifier, password);
            if (user == null) {
                // Expected outcome for wrong credentials — log for diagnostics, return null
                System.err.println("Failed login attempt for identifier: " + identifier);
                return null;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Login error: " + e.getMessage());
        }
    }
    
    @Override
    public User loginByEmail(String email, String password) throws RemoteException {
        try {
            User user = userDao.loginByEmail(email, password);
            if (user == null) {
                System.err.println("Failed email login for: " + email);
                return null;
            }
            return user;
        } catch (Exception e) {
            throw new RemoteException("Email login error: " + e.getMessage());
        }
    }
    
    @Override
    public User loginByPhone(String phoneNumber, String password) throws RemoteException {
        try {
            User user = userDao.loginByPhone(phoneNumber, password);
            if (user == null) {
                System.err.println("Failed phone login for: " + phoneNumber);
                return null;
            }
            return user;
        } catch (Exception e) {
            throw new RemoteException("Phone login error: " + e.getMessage());
        }
    }

    // OTP-related functions are defined later in this class
    
    @Override
    public boolean userExists(String nationalId) throws RemoteException {
        try {
            return userDao.userExists(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error checking user existence: " + e.getMessage());
        }
    }
    
    @Override
    public boolean emailExists(String email) throws RemoteException {
        try {
            return userDao.emailExists(email);
        } catch (Exception e) {
            throw new RemoteException("Error checking email existence: " + e.getMessage());
        }
    }
    
    @Override
    public boolean phoneExists(String phoneNumber) throws RemoteException {
        try {
            return userDao.phoneExists(phoneNumber);
        } catch (Exception e) {
            throw new RemoteException("Error checking phone existence: " + e.getMessage());
        }
    }
    
    @Override
    public User findByEmail(String email) throws RemoteException {
        try {
            return userDao.findByEmail(email);
        } catch (Exception e) {
            throw new RemoteException("Error finding user by email: " + e.getMessage());
        }
    }
    
    @Override
    public User findByPhone(String phoneNumber) throws RemoteException {
        try {
            return userDao.findByPhone(phoneNumber);
        } catch (Exception e) {
            throw new RemoteException("Error finding user by phone: " + e.getMessage());
        }
    }
    
    @Override
    public User findByNationalId(String nationalId) throws RemoteException {
        try {
            return userDao.findByNationalId(nationalId);
        } catch (Exception e) {
            throw new RemoteException("Error finding user by national ID: " + e.getMessage());
        }
    }
    
    @Override
    public boolean updatePassword(String identifier, String newPassword) throws RemoteException {
        try {
            return userDao.updatePassword(identifier, newPassword);
        } catch (Exception e) {
            throw new RemoteException("Error updating password: " + e.getMessage());
        }
    }
    
    @Override
    public List<User> findUsersByDistrict(String district) throws RemoteException {
        try {
            return userDao.findUsersByDistrict(district);
        } catch (Exception e) {
            throw new RemoteException("Error finding users by district: " + e.getMessage());
        }
    }
    
    @Override
    public List<User> searchUsersByName(String name) throws RemoteException {
        try {
            return userDao.searchUsersByName(name);
        } catch (Exception e) {
            throw new RemoteException("Error searching users by name: " + e.getMessage());
        }
    }

    @Override
    public User loginWithOtp(String email, String otp) throws RemoteException {
        try {
            // Verify OTP first
            boolean isValid = OtpService.getInstance().verifyOtp(email, otp);
            if (!isValid) {
                throw new RemoteException("Invalid or expired OTP.");
            }
            // OTP is valid, find and return user
            User user = userDao.findByEmail(email);
            if (user == null) {
                throw new RemoteException("User not found for email: " + email);
            }
            return user;
        } catch (RemoteException re) {
            throw re;
        } catch (Exception e) {
            throw new RemoteException("OTP login error: " + e.getMessage());
        }
    }

    @Override
    public boolean requestOtp(String email) throws RemoteException {
        try {
            User user = userDao.findByEmail(email);
            if (user == null) {
                throw new RemoteException("User not found for email: " + email);
            }
            String otp = OtpService.getInstance().generateOtp(email);
            boolean mqOk = false;
            if (mqPublisher != null) {
                mqOk = mqPublisher.publishOtp(email, otp);
            }
            // Fallback to direct email if MQ fails
            if (!mqOk) {
                notification.EmailSender.sendOtp(email, otp);
            }
            return true;
        } catch (Exception e) {
            throw new RemoteException("Failed to request OTP: " + e.getMessage());
        }
    }
}