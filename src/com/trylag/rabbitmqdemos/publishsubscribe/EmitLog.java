package com.trylag.rabbitmqdemos.publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;

import static com.trylag.rabbitmqdemos.constants.Constants.*;

/**
 *
 * @author Richard Haley III
 */
public class EmitLog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Create a connection factory with connection details
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONNECTION_HOST);
        Connection connection = factory.newConnection();

        // Create a channel from the connection
        Channel channel = connection.createChannel();

        // Declare the exchange in fanout (Publish/Subscribe) mode
        channel.exchangeDeclare(EXCHANGE_NAME_PUBLISH_SUBSCRIBE, "fanout");

        // Get message from command line parameters to main
        String message = getMessage(args);

        // Publish to logs exchange with empty routing key
        channel.basicPublish(EXCHANGE_NAME_PUBLISH_SUBSCRIBE, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        // Close channel and connection
        channel.close();
        connection.close();
    }

    /**
     * Checks if main method parameters array is at least one element in length,
     * and returns a concatenation of the array elements, or "Hello, World!" if
     * array size is zero.
     *
     * @param strings An array of String objects. The parameters to main.
     * @return A String object, returns Hello, World! if parameter array is
     * empty.
     */
    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "info: Hello World!";
        }
        return joinStrings(strings, " ");
    }

    /**
     * Joins String objects in an array to a single String, delimited by chosen
     * delimiter.
     *
     * @param strings An array of String objects. The parameters to main.
     * @param delimiter A String to delimit with while appending.
     * @return A String object concatenation of array elements, or an empty
     * String.
     */
    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
