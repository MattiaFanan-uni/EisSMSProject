package com.gruppo3.smslibrary.listeners;

import com.gruppo3.smslibrary.types.Message;

/**
 * @author Mattia Fanan
 * @version 1
 *
 * Interface to implement for registering a listener that receives incoming messages.
 */
public interface ReceivedMessageListener {
    void onMessageReceived(Message message);
}
