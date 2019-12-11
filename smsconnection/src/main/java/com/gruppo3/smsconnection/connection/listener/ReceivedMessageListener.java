package com.gruppo3.smsconnection.connection.listener;

import com.gruppo3.smsconnection.connection.Message;

import java.io.Serializable;

/**
 * ReceivedMessageListener interface
 *
 * @param <T> Message received data-type
 * @author Mattia Fanan
 * <p>
 * Contains the interface of the method to call when an SMS is received
 */
public interface ReceivedMessageListener<T extends Message> extends Serializable {
    /**
     * Called by NotificatonEraser whenever a message is received.
     *
     * @param message The message received
     */
    void onMessageReceived(T message);

}
