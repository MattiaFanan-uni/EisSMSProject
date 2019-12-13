package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;

/**
 * CommunicationHandler interface
 *
 * @param <T> Message data-type
 * @author Mattia Fanan
 * <p>
 * Handles SMS communications
 */
public interface CommunicationHandler<T extends Message> {
    /**
     * Sends a valid message to a peer
     *
     * @param message The message to send
     */
    boolean sendMessage(T message);

    /**
     * Adds a listener that gets called when a message is received
     *
     * @param listener The listener to wake up when a message is received
     */
    void addReceiveListener(ReceivedMessageListener<T> listener);

    /**
     * Removes the listener for received messages
     */
    void removeReceiveListener();
}