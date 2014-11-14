package com.trylag.rabbitmqdemos.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;

import static com.trylag.rabbitmqdemos.constants.Constants.*;

/**
 *
 * @author Richard Haley III
 */
public class Send {

    private static final String QUEUE_NAME = "rh11_hello";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Create a connection factory with connection details
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONNECTION_HOST);
        factory.setPort(CONNECTION_PORT);
        factory.setUsername(CONNECTION_USERNAME);
        factory.setPassword(CONNECTION_PASSWORD);
        Connection connection = factory.newConnection();

        // Create a channel from the connection
        Channel channel = connection.createChannel();

        // Declare a queue to publish to
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // Publish to the queue using the default exchange
        channel.basicPublish("", QUEUE_NAME, null, HELLO_WORLD.getBytes());
        System.out.println(" [X] Sent : '" + HELLO_WORLD + "'");

        // Close the channel and connection
        channel.close();
        connection.close();
    }
}
