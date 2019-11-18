package com.gruppo3.smsconnection.connection.listener;


import com.gruppo3.smsconnection.connection.DataUnit;

/**
 * @author  Mattia Fanan
 * scheme from gruppo1
 * Generic interface to implement to create a new listener for the DataUnit Received event.
 * @param <T> The type of message to receive
 */
public interface ReceivedMessageListener<T extends DataUnit> {

    /**
     * Called by NotificatonEraser whenever a message is received.
     * @param message The message received
     */
    void onMessageReceived(T message);

}
