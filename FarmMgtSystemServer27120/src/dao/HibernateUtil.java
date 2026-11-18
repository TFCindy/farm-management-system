package dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        try {
            // Create Configuration
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Add annotated classes
            configuration.addAnnotatedClass(model.User.class);
            configuration.addAnnotatedClass(model.Crop.class);
            configuration.addAnnotatedClass(model.Employee.class);
            configuration.addAnnotatedClass(model.EmployeeContract.class);
            configuration.addAnnotatedClass(model.Fertilizer.class);
            configuration.addAnnotatedClass(model.Livestock.class);
            
            // Build ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            
            // Build SessionFactory
            SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("Hibernate SessionFactory created!");
            return factory;
            
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}