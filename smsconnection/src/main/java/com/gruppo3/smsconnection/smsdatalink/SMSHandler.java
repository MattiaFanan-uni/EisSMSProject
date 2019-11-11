package com.gruppo3.smsconnection.smsdatalink;


import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsMessage;
import android.util.Log;

import com.gruppo3.smsconnection.connection.ReceivedMessageListener;

import java.util.ArrayList;
import java.util.Optional;

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

       // if(!message.addHeader(APP_ID + "")) //header check

        if(!message.isValid())
            return SendMessageError.NotAValidMessage;

        Optional<SMSPeer> optPeer=message.getPeer();
        if(!optPeer.isPresent())
            return SendMessageError.NotValidPeer;

        Optional<String> optText=message.getData();
        if(!optText.isPresent())
            return SendMessageError.NotValidText;

        Optional<String> optAddress=optPeer.get().getAddress();
        if(!optAddress.isPresent())
            return SendMessageError.NotValidPeer;

        String destination = optAddress.get();
        String textMessage = optText.get();

        SMSCore.sendMessage(destination,textMessage);
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
        SMSMessage message = new SMSMessage(new SMSPeer(sms.getDisplayOriginatingAddress()),content);
        if(message.isValid())
            if(smsReceivedListener == null) pendingMessages.add(message);
            else smsReceivedListener.onMessageReceived(message);
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
        NotAValidMessage,
        NotValidText,
        NotValidPeer,
        MessageSent
    }
}