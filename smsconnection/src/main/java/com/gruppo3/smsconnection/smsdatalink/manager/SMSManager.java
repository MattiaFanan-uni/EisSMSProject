package com.gruppo3.smsconnection.smsdatalink.manager;


import android.content.Context;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
/**
 * @author Mattia Fanan
 * scheme from gruppo 1
 */
public class SMSManager extends CommunicationHandler<SMSMessage> {

    private Context context;

    public SMSManager(Context context) {
        this.context=context;
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

