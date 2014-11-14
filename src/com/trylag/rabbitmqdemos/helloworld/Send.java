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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONNECTION_HOST);
        factory.setPort(CONNECTION_PORT);
        factory.setUsername(CONNECTION_USERNAME);
        factory.setPassword(CONNECTION_PASSWORD);
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    }
}
