package com.example.smsdatalink;

import android.content.Context;

public class SMSManager extends CommunicationHandler<SMSMessage> {
    // Singleton Design Pattern
    private SMSManager() { }
    private static SMSManager instance = null;
    private Context context;

    /**
     * Returns an instance of SMSManager if none exist, otherwise the one instance already created
     * as per the Singleton Design Patter, gets also the context of the application for future use
     * @param context Context of the application to use when needed
     * @return Single instance of this class
     */
    public static SMSManager getInstance(Context context) {
        if(instance == null) instance = new SMSManager();
        instance.context = context;//always same context cause one istance
        return instance;
    }

    /**
     * Adds the listener watching for incoming SMSMessages
     * @param listener The listener to wake up when a message is received
     */
    public void addReceiveListener(ReceivedMessageListener<SMSMessage> listener) {
        SMSHandler.addReceiveListener(listener);
    }

    /**
     * Removes the listener of incoming messages
     */
    public void removeReceiveListener() {
        SMSHandler.removeReceiveListener();
    }

    /**
     * Sends a given valid message
     */
    public void sendMessage(SMSMessage message) {
        SMSHandler.sendMessage(message);
    }
}

