package com.gruppo3.smsconnection.smsdatalink.manager;



import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.core.SMSCore;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;

import java.util.ArrayList;

/**
 * @author Mattia Fanan
 * manage SMS actions
 */
public final class SMSManager implements CommunicationHandler<SMSMessage> {

    private static ArrayList<SMSMessage> pendingMessages = new ArrayList<>();
    private static ReceivedMessageListener<SMSMessage> smsReceivedListener;
    private static SMSManager defInstance;

    /**
     * singleton
     */
    private SMSManager() {
        pendingMessages=retrieveSavedPendingMessages() ;
        defInstance=null;
        smsReceivedListener=null;
    }

    /**
     * get default instance for SMSManager
     * @return SMSManager
     */
    public static SMSManager getDefault(){
        if(defInstance==null)
            defInstance=new SMSManager();
        return defInstance;
    }

    /**
     * Adds the listener watching for incoming SMSMessages
     * @param listener The listener to wake up when a message is received
     */
    @Override
    public void addReceiveListener(ReceivedMessageListener<SMSMessage> listener) {
        smsReceivedListener=listener;
    }

    /**
     * Removes the listener of incoming messages
     */
    @Override
    public void removeReceiveListener() {
        smsReceivedListener=null;
    }

    /**
     * Sends a given valid message
     */
    @Override
    public boolean sendDataUnit(SMSMessage dataUnit) {

        if(dataUnit==null)
            return false;

        SMSCore.sendMessage(dataUnit);
        return true;
    }

    /**
     * handle the data unit received from the layer above
     * @param dataUnit data unit to handle
     */
    public void handleMessage(SMSMessage dataUnit)
    {
        if (dataUnit!=null){
            if (smsReceivedListener == null)
                pendingMessages.add(dataUnit);
            else
                smsReceivedListener.onMessageReceived(dataUnit);
        }
    }

    public static boolean isPendingMessagesEmpty() {
        return pendingMessages.isEmpty();
    }

    /**
     * future
     * retrive pending messages from database
     * @return
     */
    //TODO
    private ArrayList<SMSMessage> retrieveSavedPendingMessages()
    {
        return new ArrayList<SMSMessage>();
    }


    /**
     * future
     * save a pendingmessge in database
     */
    //TODO
    private void savePendingMessage(){

    }
}

