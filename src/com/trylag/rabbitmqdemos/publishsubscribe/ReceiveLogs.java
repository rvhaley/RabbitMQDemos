package com.trylag.rabbitmqdemos.publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;

import static com.trylag.rabbitmqdemos.constants.Constants.*;

/**
 *
 * @author Richard Haley III
 */
public class ReceiveLogs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        // Create a connection factory with connection details
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONNECTION_HOST);
        Connection connection = factory.newConnection();

        // Create a channel from the connection
        Channel channel = connection.createChannel();

        // Declare the exchange in fanout (Publish/Subscribe) mode
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // Create a non-durable, exclusive, autodelete queue with a generated name
        String queueName = channel.queueDeclare().getQueue();
        
        // Tell exchange to bind to this generated queue
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
