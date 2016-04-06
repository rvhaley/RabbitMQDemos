package com.trylag.rabbitmqdemos.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;

import static com.trylag.rabbitmqdemos.constants.Constants.*;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Richard Haley III
 */
public class Send {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, TimeoutException {

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
        channel.queueDeclare(HELLO_QUEUE, false, false, false, null);

        // Publish to the queue using the default exchange, the first param in basicPublish()
        channel.basicPublish("", HELLO_QUEUE, null, HELLO_MESSAGE.getBytes());
        System.out.println(" [X] Sent : '" + HELLO_MESSAGE + "'");

        // Close the channel and connection
        channel.close();
        connection.close();
    }
}
