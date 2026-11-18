/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author PC
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import service.*; 
import service.implementation.CropServiceImpl;
import service.implementation.EmployeeContractServiceImpl;
import service.implementation.EmployeeServiceImpl;
import service.implementation.FertilizerServiceImpl;
import service.implementation.LivestockServiceImpl;
import service.implementation.ReportServiceImpl;
import service.implementation.UserServiceImpl;
import notification.RabbitMqConsumer;

public class RMIServer {
    
    public static void main(String[] args) {
        // Start on a specific port in range 3000-4000
        int port = 3001; // You can change this: 3002, 3003, etc. up to 4000
        
        try {
            System.out.println("Starting RMI Server on port " + port + "...");
            
            // Check if port is in required range
            if (port < 3000 || port > 4000) {
                JOptionPane.showMessageDialog(null, 
                    "Port must be between 3000-4000. Current: " + port,
                    "Configuration Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI Registry created on port " + port);
            
            // Initialize services ONE TIME
            initializeServices(registry);
            
            // Success message
            showSuccessMessage(port);
            
            // Keep server running
            System.out.println("\nServer running. Press Ctrl+C to stop.");
            Object lock = new Object();
            synchronized (lock) {
                lock.wait();
            }
            
        } catch (RemoteException e) {
            if (e.getMessage() != null && e.getMessage().contains("already exported")) {
                System.err.println("ERROR: Service already exported!");
                System.err.println("Possible cause: Running server multiple times without restarting JVM.");
                System.err.println("Solution: Restart NetBeans or run 'taskkill /F /IM java.exe'");
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void initializeServices(Registry registry) throws RemoteException {
    System.out.println("Initializing services...");
    
    // Create service instances (they auto-export if extending UnicastRemoteObject)
    UserService userService = new UserServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();
    EmployeeContractService contractService = new EmployeeContractServiceImpl();
    CropService cropService = new CropServiceImpl();
    FertilizerService fertilizerService = new FertilizerServiceImpl();
    LivestockService livestockService = new LivestockServiceImpl();
    ReportService reportService = new ReportServiceImpl();
    
    // Just bind them - NO exportObject() calls!
    registry.rebind("UserService", userService);
    registry.rebind("EmployeeService", employeeService);
    registry.rebind("EmployeeContractService", contractService);
    registry.rebind("CropService", cropService);
    registry.rebind("FertilizerService", fertilizerService);
    registry.rebind("LivestockService", livestockService);
    registry.rebind("ReportService", reportService);
    
    System.out.println("✅ All services initialized and bound.");
    // Start RabbitMQ consumer to process OTP messages and send emails
    try {
        RabbitMqConsumer consumer = new RabbitMqConsumer("localhost", 5672, "guest", "guest");
        Thread consumerThread = new Thread(consumer, "RabbitMqConsumerThread");
        consumerThread.setDaemon(true);
        consumerThread.start();
        System.out.println("RabbitMQ consumer started in background.");
    } catch (Exception ex) {
        System.err.println("Failed to start RabbitMQ consumer: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    private static void showSuccessMessage(int port) {
        String msg = "✅ Server Started Successfully!\n\n" +
                    "Port: " + port + " (in range 3000-4000)\n" +
                    "Services: 7\n" +
                    "Status: Accepting connections";
        
        // Java 8 compatible border - no .repeat()
        String border = "==================================================";
        
        System.out.println("\n" + border);
        System.out.println(msg);
        System.out.println(border);
        
        JOptionPane.showMessageDialog(null, msg, "Server Running", JOptionPane.INFORMATION_MESSAGE);
    }
}