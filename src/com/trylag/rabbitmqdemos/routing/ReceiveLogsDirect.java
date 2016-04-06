package com.trylag.rabbitmqdemos.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;

import static com.trylag.rabbitmqdemos.constants.Constants.*;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Richard Haley III
 */
public class ReceiveLogsDirect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        // Create a connection factory with connection details
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONNECTION_HOST);
        Connection connection = factory.newConnection();

        // Create a channel from the connection
        Channel channel = connection.createChannel();

        // Declare exchange in direct mode
        channel.exchangeDeclare(EXCHANGE_NAME_ROUTING, "direct");

        // Create a non-durable, exclusive, autodelete queue with a generated name
        String queueName = channel.queueDeclare().getQueue();

        // Print severity usage info if main method arguments are empty, and exit
        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        // Iterate over main method arguments, binding direct exchange to
        // generated queue, and using severity as routing key
        for (String severity : args) {
            channel.queueBind(queueName, EXCHANGE_NAME_ROUTING, severity);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            
            // Get message body and routing key from delivery object
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}
