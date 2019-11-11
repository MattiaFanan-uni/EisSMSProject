package com.gruppo3.smsconnection.smsdatalink.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSCore extends BroadcastReceiver {

    private static final String LOG_KEY = "SMS_CORE";

    /**
     * Sends a message (SMS) to the specified target, with sent and delivery confirmation.
     * @param textMessage SMSMessage to send to the destination SMSPeer.
     */
    protected static void sendMessage(String destination,String textMessage ) {
        SmsManager.getDefault().sendTextMessage(destination,null, textMessage, null, null);
    }

    /**
     * Function called when a message is received. It delegates the message to the SMSMessage
     * Handler which analyzes its content.
     * @param context Received message context.
     * @param intent Received message Intent.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[])intent.getExtras().get("pdus");
        if(pdus!=null) {
            SmsMessage shortMessage;
            if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0],intent.getExtras().getString("format"));
            else
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
            if(shortMessage!=null){
                Log.i(LOG_KEY, "SMSMessage received");
                SMSHandler.handleMessage(shortMessage);//is running?
            }
            else
                Log.i(LOG_KEY, "SMSMessage lost");
        }
    }


}
