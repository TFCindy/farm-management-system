package notification;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EmailSender {
    // Configuration - should be loaded from properties file or environment variables
    private static String SMTP_HOST = "smtp.gmail.com";  // For Gmail
    private static int SMTP_PORT = 587;
    private static String SMTP_USER = "";  // Your email
    private static String SMTP_PASS = "";  // App password (NOT regular password)
    private static final boolean USE_AUTH = true;
    private static final boolean STARTTLS = true;
    
    // Static initialization - load from config file
    static {
        loadConfig();
    }
    
    private static void loadConfig() {
        try (InputStream input = new FileInputStream("email_config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            
            SMTP_HOST = prop.getProperty("smtp.host", "smtp.gmail.com");
            SMTP_PORT = Integer.parseInt(prop.getProperty("smtp.port", "587"));
            SMTP_USER = prop.getProperty("smtp.user", "");
            SMTP_PASS = prop.getProperty("smtp.pass", "");
            
        } catch (IOException e) {
            System.err.println("Warning: Could not load email config file, using defaults");
            // Try environment variables as fallback
            SMTP_USER = System.getenv("SMTP_USER");
            SMTP_PASS = System.getenv("SMTP_PASS");
        }
    }

    public static boolean sendOtp(String toAddress, String otp) {
        // Validate configuration
       
    System.out.println("\n=== EMAILSENDER DEBUG ===");
    System.out.println("SMTP_HOST: " + SMTP_HOST);
    System.out.println("SMTP_PORT: " + SMTP_PORT);
    System.out.println("SMTP_USER: " + SMTP_USER);
    System.out.println("SMTP_PASS loaded: " + (SMTP_PASS != null && !SMTP_PASS.isEmpty()));
    System.out.println("Sending to: " + toAddress);
    System.out.println("OTP: " + otp);
    System.out.println("=========================\n");
    
    // ... rest of your existing code ...

        if (SMTP_USER == null || SMTP_USER.isEmpty() || 
            SMTP_PASS == null || SMTP_PASS.isEmpty()) {
            System.err.println("ERROR: Email configuration missing! Check email_config.properties");
            System.err.println("SMTP_USER: " + (SMTP_USER == null ? "null" : "empty"));
            return false;
        }
        
        // Validate recipient email
        if (toAddress == null || toAddress.trim().isEmpty() || !toAddress.contains("@")) {
            System.err.println("ERROR: Invalid recipient email: " + toAddress);
            return false;
        }
        
        System.out.println("Attempting to send email to: " + toAddress);
        System.out.println("Using SMTP: " + SMTP_HOST + ":" + SMTP_PORT);
        System.out.println("From: " + SMTP_USER);
        
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", String.valueOf(USE_AUTH));
            props.put("mail.smtp.starttls.enable", String.valueOf(STARTTLS));
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", String.valueOf(SMTP_PORT));
            props.put("mail.smtp.connectiontimeout", "10000"); // 10 seconds
            props.put("mail.smtp.timeout", "10000"); // 10 seconds
            props.put("mail.smtp.writetimeout", "10000"); // 10 seconds
            
            // For debugging
            props.put("mail.debug", "true");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
                }
            });

            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject("Your OTP Code for Farm Management System");
            
            // Better formatted email - using StringBuilder for Java 8 compatibility
            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Dear User,\n\n");
            emailBody.append("Your One Time Password (OTP) is: ").append(otp).append("\n\n");
            emailBody.append("This OTP will expire in 5 minutes.\n\n");
            emailBody.append("If you did not request this OTP, please ignore this email.\n\n");
            emailBody.append("Best regards,\n");
            emailBody.append("Farm Management System Team");
            
            message.setText(emailBody.toString());

            Transport.send(message);
            System.out.println("SUCCESS: OTP email sent successfully to: " + toAddress);
            return true;
            
        } catch (AuthenticationFailedException e) {
            System.err.println("ERROR: Email authentication failed!");
            System.err.println("Check your SMTP username and password");
            System.err.println("For Gmail, use App Password not regular password");
            System.err.println("Error: " + e.getMessage());
            return false;
            
        } catch (MessagingException e) {
            System.err.println("ERROR: Failed to send email to " + toAddress);
            System.err.println("Error: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            
            // Check for specific common errors
            if (e.getMessage().contains("Could not connect to SMTP host")) {
                System.err.println("Hint: Check SMTP host/port and network connection");
                System.err.println("Host: " + SMTP_HOST + ", Port: " + SMTP_PORT);
            } else if (e.getMessage().contains("535") || e.getMessage().contains("auth")) {
                System.err.println("Hint: Authentication failed - check credentials");
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("ERROR: Unexpected error sending email to " + toAddress);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
   
}