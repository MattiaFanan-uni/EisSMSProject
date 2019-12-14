package com.gruppo3.smslibrary.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsMessage;
import android.util.Log;

import com.gruppo3.smslibrary.SmsManager;
import com.gruppo3.smslibrary.types.Message;

/**
 * This class implements the method that is called when a message is received.
 * @author Mattia Fanan, Giovanni Barca
 */
public class SmsReceiver extends BroadcastReceiver {

    /**
     * Gets an incoming {@link SmsMessage SmsMessage} and parses it to an {@link com.gruppo3.smslibrary.types.Message Message}.<br>
     * The parsed message is sent to {@link SmsManager#handleMessage(Message)}.
     *
     * @param context Received message context
     * @param intent Received message Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[])intent.getExtras().get("pdus");

        if (pdus != null) {
            SmsMessage defaultMessage;

            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
                defaultMessage = SmsMessage.createFromPdu((byte[]) pdus[0], intent.getExtras().getString("format"));
            else
                defaultMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

            if (defaultMessage != null) {
                // Try to parse defaultMessage to smslibrary.Message
                try {
                    Message message = Message.buildFromSDU(defaultMessage.getDisplayOriginatingAddress(), defaultMessage.getDisplayMessageBody());
                    SmsManager.getInstance().handleMessage(message);
                } catch (Exception e) {
                    Log.d("PDU","pdu error"); // TODO: Handle exception
                }
            }
        }
    }

}
