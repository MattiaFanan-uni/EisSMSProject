package com.gruppo3.smsconnection.smsdatalink.manager;


import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;

import java.util.ArrayList;

/**
 * @author Mattia Fanan
 * manage SMS actions
 */
public final class SMSManager extends CommunicationHandler<SMSDataUnit> {

    private static ArrayList<SMSDataUnit> pendingMessages = new ArrayList<>();
    private static ReceivedMessageListener<SMSDataUnit> smsReceivedListener;
    private static SMSManager defInstance;

    /**
     * singletone
     */
    private SMSManager() {
        pendingMessages=retreiveSavedPendingMessages();
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
    public boolean sendDataUnit(SMSDataUnit dataUnit) {
        if(dataUnit==null || !dataUnit.isValid())
            return false;

        SMSAdapter adpt;
        try{adpt=new SMSAdapter(dataUnit);}
        catch(InvalidPayloadException e){return false;}
        catch(InvalidPeerException e){return false;}

        SMSCore.sendMessage(adpt.getSMSAddress(),adpt.getSMSText());
        return true;
    }

    /**
     * handle the data unit received from the layer above
     * @param dataUnit data unit to handle
     */
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
    //TODO
    private ArrayList<SMSDataUnit> retreiveSavedPendingMessages()
    {
        return new ArrayList<SMSDataUnit>();
    }


    /**
     * future
     * save a pendingmessge in database
     */
    //TODO
    private void savePendingMessage(){

    }
}

