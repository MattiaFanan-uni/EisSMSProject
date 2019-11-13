package com.gruppo3.smsconnection.smsdatalink.manager;


import android.content.Context;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.DataUnit;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import java.util.ArrayList;

/**
 * @author Mattia Fanan
 */
public final class SMSManager extends CommunicationHandler<SMSDataUnit> {

    private static ArrayList<SMSDataUnit> pendingMessages = new ArrayList<>();
    private static ReceivedMessageListener<SMSDataUnit> smsReceivedListener;
    private static SMSManager defInstance;

    private SMSManager() {
        pendingMessages=retreiveSavedPendingMessages();
        defInstance=null;
        smsReceivedListener=null;
    }

    public static SMSManager getDefault(){
        if(defInstance==null)
            defInstance=new SMSManager();
        return defInstance;
    }

    /**
     * Adds the listener watching for incoming SMSMessages
     * @param listener The listener to wake up when a message is received
     */
    public void addReceiveListener(ReceivedMessageListener<SMSDataUnit> listener) {
        smsReceivedListener=listener;
    }

    /**
     * Removes the listener of incoming messages
     */
    public void removeReceiveListener() {
        smsReceivedListener=null;
    }

    /**
     * Sends a given valid message
     */
    public boolean sendMessage(SMSDataUnit message) {
        if(message==null || !message.isValid())
            return false;
        SMSAdapter adpt;
        try{adpt=new SMSAdapter(message);}
        catch(InvalidDataException e){return false;}
        catch(InvalidPeerException e){return false;}

        SMSCore.sendMessage(adpt.getSMSAddress(),adpt.getSMSText());
        return true;
    }

    public void handleMessage(SMSDataUnit dataUnit)
    {
        if (dataUnit!=null && dataUnit.isValid()){
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
    private ArrayList<SMSDataUnit> retreiveSavedPendingMessages()
    {
        return new ArrayList<SMSDataUnit>();
    }


    /**
     * future
     * save a pendingmessge in database
     */
    private void savePendingMessage(){

    }
}

