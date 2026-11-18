package notification2;

import notification.RabbitMqConsumer;
import notification.RabbitMqPublisher;

public class FinalOtpTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== FINAL OTP SYSTEM TEST ===\n");
        
        // 1. Start Consumer
        System.out.println("1. Starting Consumer...");
        RabbitMqConsumer consumer = new RabbitMqConsumer("localhost", 5672, "guest", "guest");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        Thread.sleep(3000);
        
        // 2. Publish Message
        System.out.println("\n2. Publishing to RabbitMQ...");
        RabbitMqPublisher publisher = new RabbitMqPublisher("localhost", 5672, "guest", "guest");
        publisher.publishOtp("cindymun59@gmail.com", "999999");
        
        // 3. Wait and check
        System.out.println("\n3. Waiting 10 seconds...");
        Thread.sleep(10000);
        
        System.out.println("\n=== TEST COMPLETE ===");
        System.out.println("Check email at: cindymun59@gmail.com");
        System.out.println("Also check SPAM folder!");
        
        consumerThread.interrupt();
    }
}