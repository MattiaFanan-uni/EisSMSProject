package com.gruppo3.smsconnection.smsdatalink.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;

/**
 *@author Mattia Fanan
 *
 * basic class scheme from gruppo1
 * it interfaces SMSMDataUnit with API
 */
public class SMSCore extends BroadcastReceiver {


    /**
     * Sends APIMessage
     *
     * @param apiMessage APIMessage message to send
     */
    public static void sendMessage(APIMessage apiMessage) {
        SmsManager.getDefault().sendTextMessage(apiMessage.getDestination(), null, apiMessage.getTextMessage(), null, null);
    }

    /**
     * Function called when a message is received. It delegates the message to the SMSDataUnit
     * Handler which analyzes its content.
     *
     * @param context Received message context.
     * @param intent  Received message Intent.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        if (pdus != null) {
            SmsMessage shortMessage;
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0], intent.getExtras().getString("format"));
            else
                shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

            if (shortMessage != null) {
                APIMessage apiMessage=new APIMessage(null, shortMessage.getDisplayOriginatingAddress(), shortMessage.getDisplayMessageBody());

                //try to pass the SMSDataUnit built from incoming Sms to SMSManager
                try {
                    SMSManager.getDefault().handleMessage(SMSAdapter.adaptToSMSDataUnit(apiMessage));
                }
                catch (InvalidPayloadException e) { }
                catch (InvalidPeerException e) { }
                catch (InvalidHeaderException e) { }
            }
        }
    }

}
