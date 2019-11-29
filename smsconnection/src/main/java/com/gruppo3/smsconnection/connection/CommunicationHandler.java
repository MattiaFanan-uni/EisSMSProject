package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;

/**
 * CommunicationHandler interface
 * @author Mattia Fanan
 *
 * Handles SMS communications
 * @param <T> Message data-type
 */
public interface CommunicationHandler<T extends Message> {
    /**
     * Sends a valid message to a peer
     * @param dataUnit The message to send
     */
    boolean sendDataUnit(T dataUnit);

    /**
     * Adds a listener that gets called when a message is received
     * @param listener The listener to wake up when a message is received
     */
    void addReceiveListener(ReceivedMessageListener<T> listener);

    /**
     * Removes the listener for received messages
     */
    void removeReceiveListener();
}