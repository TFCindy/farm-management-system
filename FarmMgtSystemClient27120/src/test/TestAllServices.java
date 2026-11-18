package test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestAllServices {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3001);
            
            System.out.println("Testing all RMI services...\n");
            
            // Test each service
            String[] services = {
                "UserService",
                "EmployeeService", 
                "EmployeeContractService",
                "CropService",
                "FertilizerService",
                "LivestockService"
            };
            
            for (String serviceName : services) {
                try {
                    Object service = registry.lookup(serviceName);
                    System.out.println("✅ " + serviceName + " - Connected successfully");
                } catch (Exception e) {
                    System.out.println("❌ " + serviceName + " - Failed: " + e.getMessage());
                }
            }
            
            System.out.println("\n✅ All services tested!");
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}