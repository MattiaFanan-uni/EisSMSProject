package com.gruppo3.smslibrary;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gruppo3.smslibrary.listeners.ReceivedMessageListener;
import com.gruppo3.smslibrary.types.Message;

/**
 * Singleton class that manages SMS operations via smslibrary data-types, interfacing with system SmsManager library.
 *
 * @author Mattia Fanan. Reviewed by Giovanni Barca. Corrected by Giovanni Barca.
 * @version 1
 */
public class SmsManager {
    private static ReceivedMessageListener receivedMessageListener;
    private static SmsManager instance;

    /**
     * Initializes a newly created SmsManager object.
     */
    private SmsManager() {
        receivedMessageListener = null;
    }

    /**
     * Gets the current SmsManager instance.
     *
     * @return An SmsManager instance
     */
    public static SmsManager getInstance() {
        if (instance == null)
            instance = new SmsManager();
        return instance;
    }

    /**
     * Gets the default instance of SmsManager.<br>
     * When this method is called, the <code>receivedMessageListener</code> is set to <code>null</code>.
     *
     * @return SmsManager default instance
     */
    public static SmsManager getDefault() {
        instance = new SmsManager();
        return instance;
    }

    /**
     * Subscribes to a listener watching for incoming messages.
     *
     * @param receivedMessageListener The listener to wake up when a message is received
     */
    public void addReceivedMessageListener(ReceivedMessageListener receivedMessageListener) {
        this.receivedMessageListener = receivedMessageListener;
    }

    /**
     * Unsubscribes from the registered listener.
     */
    public void removeReceivedMessageListener() {
        receivedMessageListener = null;
    }

    /**
     * Sends the message passed in the argument to the Peer specified in the Message properties.<br>
     * Note: Using this method requires that your app has the <code>Manifest.permission.SEND_SMS</code> permission.
     * @param message Message to be sent
     * @throws IllegalArgumentException If the destination peer is null
     */
    public void sendMessage(@NonNull Message message) throws IllegalArgumentException {
        if (message.getDestination() == null)
            throw new IllegalArgumentException();

        android.telephony.SmsManager.getDefault().sendTextMessage(message.getDestination().getPhoneNumber(), null, message.getSDU(), null, null);
    }

    /**
     * Handles the received (and parsed) message and sends it to the <code>receivedMessageListener</code>
     * @param message Message to be handled and delegated to the registered listener
     */
    public void handleMessage(@NonNull Message message) {
        try {
            receivedMessageListener.onMessageReceived(message);
        }
        catch (Exception ex) {
            Log.e("EIS", ex.toString()); // TODO: Handle exception
        }
    }
}
