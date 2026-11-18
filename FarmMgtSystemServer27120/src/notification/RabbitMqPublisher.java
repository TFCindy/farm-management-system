package notification;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.AMQP;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RabbitMqPublisher {
    private static final String QUEUE_NAME = "otp_queue";
    private final ConnectionFactory factory;

    public RabbitMqPublisher(String host, int port, String username, String password) {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/"); // Explicitly set virtual host
        
        // Set additional connection parameters
        factory.setConnectionTimeout(30000);
        factory.setHandshakeTimeout(30000);
        factory.setRequestedHeartbeat(60);
        factory.setAutomaticRecoveryEnabled(true);
    }

    public boolean publishOtp(String email, String otp) {
        String payload = email + ":" + otp;
        Connection connection = null;
        Channel channel = null;
        
        try {
            // Create connection
            connection = factory.newConnection();
            System.out.println("✓ Connected to RabbitMQ");
            System.out.println("  Host: " + factory.getHost() + ":" + factory.getPort());
            System.out.println("  Virtual Host: " + factory.getVirtualHost());
            
            // Create channel
            channel = connection.createChannel();
            System.out.println("✓ Channel created");
            
            // Declare queue with explicit arguments
            Map<String, Object> args = new HashMap<>();
            boolean durable = true;
            boolean exclusive = false;
            boolean autoDelete = false;
            
            channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, args);
            System.out.println("✓ Queue '" + QUEUE_NAME + "' declared/verified");
            System.out.println("  Durable: " + durable + ", AutoDelete: " + autoDelete);
            
            // Check queue status BEFORE publishing
            AMQP.Queue.DeclareOk before = channel.queueDeclarePassive(QUEUE_NAME);
            System.out.println("  Messages in queue before: " + before.getMessageCount());
            
            // Publish message
            channel.basicPublish(
                "", 
                QUEUE_NAME, 
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                payload.getBytes(StandardCharsets.UTF_8)
            );
            
            System.out.println("✓ Message published");
            System.out.println("  To: " + email);
            System.out.println("  Payload: " + payload);
            
            // Check queue status AFTER publishing
            AMQP.Queue.DeclareOk after = channel.queueDeclarePassive(QUEUE_NAME);
            System.out.println("  Messages in queue after: " + after.getMessageCount());
            
            if (after.getMessageCount() > before.getMessageCount()) {
                System.out.println("✅ Message successfully added to queue!");
            } else {
                System.out.println("⚠️  Message count didn't increase - check consumers!");
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Failed to publish OTP message!");
            System.err.println("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            return false;
            
        } finally {
            // Close resources properly
            try {
                if (channel != null && channel.isOpen()) {
                    channel.close();
                    System.out.println("✓ Channel closed");
                }
            } catch (Exception e) {
                System.err.println("  Warning: Error closing channel: " + e.getMessage());
            }
            
            try {
                if (connection != null && connection.isOpen()) {
                    connection.close();
                    System.out.println("✓ Connection closed");
                }
            } catch (Exception e) {
                System.err.println("  Warning: Error closing connection: " + e.getMessage());
            }
        }
    }

    public void runDiagnostic() {
        System.out.println("\n🔍 Running Diagnostic Check...");
        System.out.println("================================");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            System.out.println("1. Connection Details:");
            System.out.println("   Host: " + factory.getHost());
            System.out.println("   Port: " + factory.getPort());
            System.out.println("   Username: " + factory.getUsername());
            System.out.println("   Virtual Host: " + factory.getVirtualHost());
            
            System.out.println("\n2. Checking Queue Status:");
            try {
                AMQP.Queue.DeclareOk queueInfo = channel.queueDeclarePassive(QUEUE_NAME);
                System.out.println("   Queue: " + QUEUE_NAME);
                System.out.println("   Messages: " + queueInfo.getMessageCount());
                System.out.println("   Consumers: " + queueInfo.getConsumerCount());
                
                if (queueInfo.getConsumerCount() > 0) {
                    System.out.println("   ⚠️  ALERT: " + queueInfo.getConsumerCount() + 
                                     " active consumer(s) detected!");
                    System.out.println("      Messages may be consumed immediately.");
                }
            } catch (Exception e) {
                System.out.println("   Queue doesn't exist or error: " + e.getMessage());
            }
            
            System.out.println("\n3. Testing Message Publication...");
            
            // Test with a unique payload
            String testEmail = "diagnostic-" + System.currentTimeMillis() + "@test.com";
            String testOtp = "999999";
            
            Connection pubConn = factory.newConnection();
            Channel pubChannel = pubConn.createChannel();
            
            // Check before
            AMQP.Queue.DeclareOk before = pubChannel.queueDeclarePassive(QUEUE_NAME);
            int beforeCount = before.getMessageCount();
            System.out.println("   Messages before test: " + beforeCount);
            
            // Publish test message
            String payload = testEmail + ":" + testOtp;
            pubChannel.basicPublish("", QUEUE_NAME, 
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                payload.getBytes(StandardCharsets.UTF_8));
            
            // Check after
            AMQP.Queue.DeclareOk after = pubChannel.queueDeclarePassive(QUEUE_NAME);
            int afterCount = after.getMessageCount();
            System.out.println("   Messages after test: " + afterCount);
            
            if (afterCount > beforeCount) {
                System.out.println("   ✅ SUCCESS: Message added to queue!");
                System.out.println("      Queue delta: +" + (afterCount - beforeCount));
            } else {
                System.out.println("   ❌ FAILURE: Message count unchanged!");
                System.out.println("      Check for active consumers or queue configuration.");
            }
            
            pubChannel.close();
            pubConn.close();
            
        } catch (Exception e) {
            System.err.println("Diagnostic failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
}