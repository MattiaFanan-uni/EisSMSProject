package com.gruppo3.smsconnection.smsdatalink.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;

/**
 *@author Mattia Fanan
 *
 * basic class scheme from gruppo1
 * it interfaces SMSMDataUnit with API
 */
public class SMSCore extends BroadcastReceiver {


    /**
     * Sends a message (SMS) to the specified target, with sent and delivery confirmation.
     * @param textMessage SMSDataUnit to send to the destination SMSPeer.
     */
    protected static void sendMessage(String destination,String textMessage ) {
        SmsManager.getDefault().sendTextMessage(destination,null, textMessage, null, null);
    }

    /**
     * Function called when a message is received. It delegates the message to the SMSDataUnit
     * Handler which analyzes its content.
     * @param context Received message context.
     * @param intent Received message Intent.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean error=false;
        Object[] pdus = (Object[])intent.getExtras().get("pdus");
        if(pdus!=null) {
            SmsMessage shortMessage;
            if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0],intent.getExtras().getString("format"));
            else
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

            if(shortMessage!=null){

                //adapt the smsMessage to SMSDataUnit
                SMSAdapter adpt=null;
                try {
                    adpt = new SMSAdapter(shortMessage.getDisplayOriginatingAddress(),shortMessage.getDisplayMessageBody());
                }
                catch (InvalidPayloadException e){error=true;}
                catch (InvalidPeerException e){error=true;}
                catch (InvalidHeaderException e){error=true;}

                //if adapter build successfully//adapter also check if is a message to our app
                if(!error)
                    SMSManager.getDefault().handleMessage(adpt.getSMSDataUnit());
            }
        }
    }


}
