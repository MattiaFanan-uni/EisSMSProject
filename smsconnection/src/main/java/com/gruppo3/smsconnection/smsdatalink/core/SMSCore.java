package com.gruppo3.smsconnection.smsdatalink.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;

/**
 * @author Mattia Fanan
 * <p>
 * basic class scheme from gruppo1
 * it interfaces SMSMDataUnit with API
 */
public class SMSCore extends BroadcastReceiver {


    /**
     * Sends a data unit
     *
     * @param dataUnit SMSMessage to send
     */
    public static void sendMessage(SMSMessage dataUnit) {
        try {
            String a = new String(dataUnit.getSDU(), "UTF-16");
            SmsManager.getDefault().sendTextMessage(dataUnit.getDestinationPeer().getAddress(), null, new String(dataUnit.getSDU(), "UTF-16"), null, null);
        } catch (Exception e) {
        }
    }

    /**
     * Function called when a message is received. It delegates the message to the SMSMessage
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

                //try to pass the SMSMessage built from incoming Sms to SMSManager
                try {
                    SMSManager.getDefault().handleMessage(
                            SMSMessage.buildFromSDU(
                                    shortMessage.getDisplayOriginatingAddress(),
                                    shortMessage.getDisplayMessageBody().getBytes("UTF-16")
                            )
                    );
                } catch (Exception e) {
                }
            }
        }
    }

}
