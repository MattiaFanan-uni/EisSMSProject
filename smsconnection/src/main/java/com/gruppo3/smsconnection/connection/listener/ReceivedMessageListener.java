package com.gruppo3.smsconnection.connection.listener;

import com.gruppo3.smsconnection.connection.DataUnit;

/**
 * ReceivedMessageListener interface
 * @author Mattia Fanan
 *
 * Contains the interface of the method to call when an SMS is received
 * @param <T> Message received data-type
 */
public interface ReceivedMessageListener<T extends DataUnit> {
    /**
     * Called by NotificatonEraser whenever a message is received.
     * @param message The message received
     */
    void onMessageReceived(T message);

}
