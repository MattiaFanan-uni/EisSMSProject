package com.gruppo3.smsconnection.smsdatalink.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

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
     * @param message SMSMessage to send
     */
    public static void sendMessage(SMSMessage message) {
        try {
            Log.d("PDU", "sending length "+message.getSDU().length());
            SmsManager.getDefault().sendTextMessage(message.getDestinationPeer().getAddress(), null, message.getSDU(), null, null);
        } catch (Exception e) {
            Log.d("PDU", "sending problem pdu");
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
                    SMSMessage message=SMSMessage.buildFromSDU(
                            shortMessage.getDisplayOriginatingAddress(),
                            shortMessage.getDisplayMessageBody()
                    );
                    SMSManager.getDefault().handleMessage(message);
                    Log.d("PDU","pdu length : "+shortMessage.getDisplayMessageBody().length());
                } catch (Exception e) {
                    Log.d("PDU","pdu error");
                }
            }
        }
    }

}
