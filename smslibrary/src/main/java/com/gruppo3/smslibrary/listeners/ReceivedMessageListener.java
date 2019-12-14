package com.gruppo3.smslibrary.listeners;

/**
 * Interface to implement for registering a listener that receives incoming messages.
 * @author Mattia Fanan, Giovanni Barca
 */
public interface ReceivedMessageListener<Message> {
    void onMessageReceived(Message message);
}
