package notification;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMqConsumer implements Runnable {
    private static final String QUEUE_NAME = "otp_queue";
    private final ConnectionFactory factory;
    private volatile boolean running = true;

    public RabbitMqConsumer(String host, int port, String username, String password) {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost("/");
        if (username != null) factory.setUsername(username);
        if (password != null) factory.setPassword(password);
        factory.setAutomaticRecoveryEnabled(true);
    }

    @Override
    public void run() {
        System.out.println("=== RABBITMQ CONSUMER STARTING ===");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            // Declare queue with same parameters as publisher
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicQos(1); // Process one message at a time
            
            System.out.println("✅ Connected to RabbitMQ");
            System.out.println("   Queue: " + QUEUE_NAME);
            System.out.println("   Waiting for messages...\n");
            
            // Create consumer with detailed logging
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("==========================================");
                System.out.println("📩 RECEIVED MESSAGE: " + message);
                System.out.println("==========================================");
                
                try {
                    // Parse the message
                    String[] parts = message.split(":", 2);
                    if (parts.length != 2) {
                        System.err.println("❌ ERROR: Invalid message format");
                        System.err.println("   Expected: email:otp");
                        System.err.println("   Got: " + message);
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                        return;
                    }
                    
                    String email = parts[0].trim();
                    String otp = parts[1].trim();
                    
                    System.out.println("   Email: " + email);
                    System.out.println("   OTP: " + otp);
                    
                    // Validate
                    if (!email.contains("@")) {
                        System.err.println("❌ ERROR: Invalid email address");
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                        return;
                    }
                    
                    if (otp.length() < 4) {
                        System.err.println("❌ ERROR: OTP too short");
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                        return;
                    }
                    
                    // Try to send email with detailed error handling
                    System.out.println("\n   📧 CALLING EmailSender.sendOtp()...");
                    
                    try {
                        // Add debug to check if EmailSender is working
                        System.out.println("   Checking EmailSender class...");
                        Class.forName("notification.EmailSender");
                        System.out.println("   ✅ EmailSender class found");
                        
                        // Call the email sender
                        boolean emailSent = EmailSender.sendOtp(email, otp);
                        
                        if (emailSent) {
                            System.out.println("\n   ✅ SUCCESS: EmailSender returned TRUE");
                            System.out.println("   Email should be sent to: " + email);
                            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        } else {
                            System.err.println("\n   ❌ FAILURE: EmailSender returned FALSE");
                            System.err.println("   This means EmailSender failed to send email");
                            System.err.println("   Check EmailSender error messages above");
                            
                            // Requeue the message for retry
                            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                            System.err.println("   ↪️ Message requeued for retry");
                        }
                        
                    } catch (ClassNotFoundException e) {
                        System.err.println("\n   ❌ CRITICAL: EmailSender class not found!");
                        System.err.println("   Make sure EmailSender.java is in notification package");
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                    }
                    
                } catch (Exception e) {
                    System.err.println("\n   ❌ UNEXPECTED ERROR: " + e.getClass().getSimpleName());
                    System.err.println("   Message: " + e.getMessage());
                    e.printStackTrace();
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                }
                
                System.out.println("\n==========================================\n");
            };
            
            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("Consumer cancelled: " + consumerTag);
            };
            
            // Start consuming (manual acknowledgment)
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
            
            System.out.println("✅ Consumer is now actively listening for messages");
            System.out.println("Press Ctrl+C to stop\n");
            
            // Keep the thread alive
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
        } catch (IOException | TimeoutException e) {
            System.err.println("\n❌ CONSUMER FAILED TO START");
            System.err.println("Error: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            
            if (e.getMessage().contains("Connection refused")) {
                System.err.println("\n💡 TROUBLESHOOTING:");
                System.err.println("1. Is RabbitMQ running?");
                System.err.println("2. Check: rabbitmqctl status");
                System.err.println("3. Default credentials: guest/guest");
            }
            
            e.printStackTrace();
        }
        
        System.out.println("\n🔌 Consumer stopped");
    }
    
    public void stop() {
        running = false;
    }
    

}