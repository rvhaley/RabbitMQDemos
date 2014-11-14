package com.trylag.rabbitmqdemos.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.trylag.rabbitmqdemos.constants.Constants.*;

/**
 *
 * @author Richard Haley III
 */
public class EmitLogTopic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection connection = null;
        Channel channel = null;

        try {
            // Create a connection factory with connection details
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(CONNECTION_HOST);
            connection = factory.newConnection();

            // Create a channel from the connection
            channel = connection.createChannel();

            // Send messages to exchange in topic mode
            channel.exchangeDeclare(EXCHANGE_NAME_TOPICS, "topic");

            // Get routing key and log message
            String routingKey = getRouting(args);
            String message = getMessage(args);

            // Publish to topic exchange with routing key from main arguments
            channel.basicPublish(EXCHANGE_NAME_TOPICS, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * Gets log routing key from first element in main arguments String array.
     * Returns "anonymous.info" if array is empty.
     *
     * @param strings An array of String objects. The parameters to main.
     * @return A String object, returns "anonymous.info" if parameter array is
     * empty.
     */
    private static String getRouting(String[] strings) {
        if (strings.length < 1) {
            return "anonymous.info";
        }
        return strings[0];
    }

    /**
     * Gets the log message from a concatenation of the String array elements,
     * starting with the second element by using the joinStrings() helper
     * method. If array contains only one element, Hello, World! is returned for
     * the message.
     *
     * @param strings An array of String objects. The parameters to main.
     * @return A String object, returns "Hello, World!" for the message if
     * parameter array contains only severity element
     */
    private static String getMessage(String[] strings) {
        if (strings.length < 2) {
            return "Hello World!";
        }
        return joinStrings(strings, " ", 1);
    }

    /**
     * Joins String objects in an array to a single String, delimited by chosen
     * delimiter, and starting from a specific index.
     *
     * @param strings An array of String objects. The parameters to main.
     * @param delimiter A String to delimit with while appending.
     * @param startIndex The integer index of the String array to begin joining
     * at.
     * @return A String object concatenation of array elements, or an empty
     * String.
     */
    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        if (length < startIndex) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
