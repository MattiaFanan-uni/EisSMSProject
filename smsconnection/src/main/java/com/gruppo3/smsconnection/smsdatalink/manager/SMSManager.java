package com.gruppo3.smsconnection.smsdatalink.manager;


import android.util.Log;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.core.SMSCore;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;

import java.util.ArrayList;

/**
 * @author Mattia Fanan
 * manage SMS actions
 */
public final class SMSManager implements CommunicationHandler<SMSMessage> {

    private static ReceivedMessageListener<SMSMessage> smsReceivedListener;
    private static SMSManager defInstance;

    /**
     * singleton
     */
    private SMSManager() {

        Log.d("MESSAGE","sms in manager UP");
        defInstance = null;
        smsReceivedListener = null;
    }

    /**
     * get default instance for SMSManager
     *
     * @return SMSManager
     */
    public static SMSManager getDefault() {
        if (defInstance == null)
            defInstance = new SMSManager();
        return defInstance;
    }

    /**
     * Adds the listener watching for incoming SMSMessages
     *
     * @param listener The listener to wake up when a message is received
     */
    @Override
    public void addReceiveListener(ReceivedMessageListener<SMSMessage> listener) {

        Log.d("MESSAGE","listener sms up");
        smsReceivedListener = listener;
    }

    /**
     * Removes the listener of incoming messages
     */
    @Override
    public void removeReceiveListener() {
        smsReceivedListener = null;
    }

    /**
     * Sends a given valid message
     */
    @Override
    public boolean sendMessage(SMSMessage message) {

        if (message == null) {
            Log.d("MESSAGE","null sms in manager send");
            return false;
        }

        Log.d("MESSAGE","sms in manager send");
        SMSCore.sendMessage(message);
        return true;
    }

    /**
     * handle the message received from the layer above
     *
     * @param message message to be handled
     */
    public void handleMessage(SMSMessage message) {
        if (message != null) {
            Log.d("MESSAGE","sms in manager receive");
            smsReceivedListener.onMessageReceived(message);
        }
        else
            Log.d("MESSAGE","null sms in manager receive");
    }
}

