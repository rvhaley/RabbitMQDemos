package com.trylag.rabbitmqdemos.constants;

/**
 *
 * @author Richard Haley III
 */
public interface Constants {

    /**
     * Connection details
     */
    public static final String CONNECTION_HOST = "datdb.cphbusiness.dk";
    public static final int CONNECTION_PORT = 5672;
    public static final String CONNECTION_USERNAME = "student";
    public static final String CONNECTION_PASSWORD = "cph";

    /**
     * Hello, world!
     */
    public static final String HELLO_MESSAGE = "Hello, World!";
    public static final String HELLO_QUEUE = "rh11_hello";

    /**
     * Work Queues
     */
    public static final String TASK_QUEUE_NAME = "rh11_task_queue";
}
