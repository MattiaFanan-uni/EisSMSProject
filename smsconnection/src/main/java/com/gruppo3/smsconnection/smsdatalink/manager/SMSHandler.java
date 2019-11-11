package com.gruppo3.smsconnection.smsdatalink.manager;


import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsMessage;

import com.gruppo3.smsconnection.connection.Exceptions.InvalidDataException;
import com.gruppo3.smsconnection.connection.Exceptions.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPayloadData;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import java.util.ArrayList;

public class SMSHandler extends NotificationListenerService {

    private static final char APP_ID = (char)0x02;
    private static final String LOG_KEY = "SMS_HANDLER";
    private static ArrayList<SMSMessage> pendingMessages = new ArrayList<>();

    private static ReceivedMessageListener smsReceivedListener;

    /**
     * Sends a valid SMSmessage, with sent and delivery confirmation.
     * @param message SMSMessage to send to the SMSPeer.
     * @throws IllegalArgumentException if the destination is invalid
     */
    public static SendMessageError sendMessage(SMSMessage message) {

       // if(!message.addHeader(APP_ID + "")) //put header

        if(!message.getPayloadData().isValid())
            return SendMessageError.NotValidPayload;

        if(!message.getPeer().isValid())
            return SendMessageError.NotValidPeer;

        SMSCore.sendMessage(message.getPeer().getAddress(),message.getPayloadData().getData());
        return SendMessageError.MessageSent;
    }

    /**
     * Sets the listener, that is the object to be called when an SMS with the APP_ID is received.
     * @param listener Must be an object that implements the ReceivedMessageListener<SMSMessage> interface.
     */
    public static void addReceiveListener(ReceivedMessageListener<SMSMessage> listener) {
        smsReceivedListener = listener;
        for (SMSMessage pendingMessage : pendingMessages) smsReceivedListener.onMessageReceived(pendingMessage);
        pendingMessages.clear();
    }

    /**
     * Removes a listener from listening to incoming messages
     */
    public static void removeReceiveListener() {
        smsReceivedListener = null;
    }

    /**
     * Analyze the message received by SMSCore, if the APP_ID is recognized it calls the listener.
     * @param sms The object representing the short message.
     */
    protected static void handleMessage(SmsMessage sms) {
        String content = sms.getDisplayMessageBody();
        /*if(content.charAt(0) != APP_ID) return;
        SMSMessage message = new SMSMessage(
                new SMSPeer(sms.getDisplayOriginatingAddress()),
                content.substring(1));*/
        SMSPeer peer=null;
        SMSPayloadData data=null;

        try{peer=new SMSPeer(sms.getOriginatingAddress());}
        catch(InvalidPeerException e){}

        try{data=new SMSPayloadData(content);}
        catch(InvalidDataException e){}

        if(peer!=null && data!=null) {

            SMSMessage message = null;

            try{message=new SMSMessage(data,peer);}
            catch (InvalidPeerException e){}
            catch (InvalidDataException e){}

            if (message!=null)
                if (smsReceivedListener == null) pendingMessages.add(message);
                else smsReceivedListener.onMessageReceived(message);
        }
    }

    /**
     * Returns true if there's no pending message, false otherwise
     */
    public static boolean isPendingMessagesEmpty() {
        return pendingMessages.isEmpty();
    }

    /**
     * Overridden method that catches the notifications. If a messaging notification is
     * recognized and it contains the APP_ID than it will be cancelled.
     * @param sbn StatusBarNotification object that contains all the notification informations.
     */
    @Override
    public void onNotificationPosted (StatusBarNotification sbn) {
        if(sbn.getPackageName().equals("com.google.android.apps.messaging")
                && sbn.getNotification().tickerText.toString().contains(APP_ID + ""))
        cancelNotification(sbn.getKey());
    }

    @Override
    public void onDestroy(){
        //save pending messages
    }
    @Override
    public void onCreate(){
        super.onCreate();
        //retreive pending messages
    }

    public enum SendMessageError{
        NotValidPayload,
        NotValidPeer,
        MessageSent
    }
}