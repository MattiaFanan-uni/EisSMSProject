package com.gruppo3.eissmsproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gruppo3.kademlia.NetworkManager;
import com.gruppo3.smslibrary.SmsManager;
import com.gruppo3.smslibrary.listeners.ReceivedMessageListener;
import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;

public class MainActivity extends AppCompatActivity {

    private final String tag = "EIS"; // Tag string for test-logging purpose

    // UI object declaration
    private TextView textView_actualPhoneNumber;
    private TextView textView_sourcePeer;
    private TextView textView_messagePayload;
    private EditText editText_destinationPhoneNumber;
    private EditText editText_messageText;

    private String phoneNumber = ""; // Contains current device phone number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // # UI Object assignment #
        getUIElements();

        // # Initial operations #
        textView_actualPhoneNumber.setText("Actual phone number: " + getPhoneNumber());

        // Setting onMessageReceived listener that shows last received message sender and payload on the screen
        SmsManager.getInstance().addReceivedMessageListener(new ReceivedMessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                textView_sourcePeer.setText("Source peer: " + message.getSource().getPhoneNumber());
                textView_messagePayload.setText("Message payload: " + message.getPayload());
            }
        });
    }

    /**
     * Assign UI elements to local variables
     */
    private void getUIElements() {
        textView_actualPhoneNumber = findViewById(R.id.textViewActualPhoneNumber);
        textView_sourcePeer = findViewById(R.id.textViewSourcePeer);
        textView_messagePayload = findViewById(R.id.textViewMessagePayload);
        editText_destinationPhoneNumber = findViewById(R.id.editTextDestinationPhoneNumber);
        editText_messageText = findViewById(R.id.editTextMessageText);
    }

    /**
     * Gets actual device phone number
     * @return A String containing the current device phone number
     */
    private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        // If not already given, request READ_PHONE_STATE permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.READ_PHONE_STATE }, 2);
        }

        phoneNumber = tMgr.getLine1Number();

        return phoneNumber;
    }

    /**
     * btnSendMessage button onClick event
     */
    public void btnSendMessageOnClick(View v) {
        try {
            NetworkManager nm = new NetworkManager(phoneNumber); // Initializes a new NetworkManager instance passing current device phone number (NetworkManager manages a kademlia network)
            Peer destinationPeer = new Peer(editText_destinationPhoneNumber.getText().toString()); // destinationPeer will be the bootstrap node for current device
        }
        catch (Exception e) { // For testing our "demo" we catch all exceptions
            Log.e(tag, Log.getStackTraceString(e)); // Printing every given exception in the Logcat
        }
    }
}
