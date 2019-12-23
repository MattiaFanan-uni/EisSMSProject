package com.gruppo3.smslibrary.listeners;

import com.gruppo3.smslibrary.types.Message;

/**
 * Interface to implement for registering a listener that receives incoming messages.
 *
 * @author Mattia Fanan
 * @version 1
 */
public interface ReceivedMessageListener {
    void onMessageReceived(Message message);
}
