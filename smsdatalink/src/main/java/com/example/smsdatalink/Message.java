package com.example.smsdatalink;

/**
 * Interface to implement to create a new Message type
 */
public interface Message {
    /**
     * Returns the data contained in the message
     */
    String getData();

    /**
     * Returns the Peer of the message
     */
    Peer getPeer();
}

