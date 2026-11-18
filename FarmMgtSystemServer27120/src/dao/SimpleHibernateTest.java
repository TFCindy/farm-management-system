package dao;

public class SimpleHibernateTest {
    public static void main(String[] args) {
        System.out.println("=== SIMPLE HIBERNATE DIAGNOSTIC ===");
        
        try {
            // Just try to get the session factory
            Object factory = HibernateUtil.getSessionFactory();
            System.out.println("✅ Hibernate initialized successfully!");
            System.out.println("SessionFactory class: " + factory.getClass().getName());
            
        } catch (Exception e) {
            System.err.println("❌ Hibernate initialization failed!");
            System.err.println("Error type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            
            // Check the cause
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
}