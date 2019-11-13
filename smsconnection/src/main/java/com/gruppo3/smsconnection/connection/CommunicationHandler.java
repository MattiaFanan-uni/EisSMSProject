package com.gruppo3.smsconnection.connection;


import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;

/**
 * @authr Mattia Fanan
 * scheme from gruppo 1
 * Generic class for handling communications. Extend this to implement a new communication type
 * @param <T> For a communication to work, this must be specific for a type of message
 */
public abstract class CommunicationHandler<T extends DataUnit> {
    /**
     * Sends a valid message to a valid Peer
     * @param message The message to send
     */
    public abstract boolean sendMessage(T message);

    /**
     * Adds a listener that gets called when a message for the library is received
     * @param listener The listener to wake up when a message is received
     */
    public abstract void addReceiveListener(ReceivedMessageListener<T> listener);

    /**
     * Removes the listener waiting for incoming messages
     */
    public abstract void removeReceiveListener();
}