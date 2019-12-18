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

    // UI object declaration
    private TextView textView_actualPhoneNumber;
    private TextView textView_sourcePeer;
    private TextView textView_messagePayload;
    private EditText editText_destinationPhoneNumber;
    private EditText editText_messageText;

    String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // # UI Object assignment #
        textView_actualPhoneNumber = findViewById(R.id.textView_actualPhoneNumber);
        textView_sourcePeer = findViewById(R.id.textView_sourcePeer);
        textView_messagePayload = findViewById(R.id.textView_messagePayload);
        editText_destinationPhoneNumber = findViewById(R.id.editText_destinationPhoneNumber);
        editText_messageText = findViewById(R.id.editText_messageText);

        // # Initial operations #
        showActualPhoneNumber();
        SmsManager.getInstance().addReceivedMessageListener(new ReceivedMessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                textView_sourcePeer.setText("Source peer: " + message.getSource().getPhoneNumber());
                textView_messagePayload.setText("Message payload: " + message.getPayload());
            }
        });
    }

    /**
     * Gets actual device phone number and shows it on the screen
     */
    public void showActualPhoneNumber() {
        String mPhoneNumber = "Actual phone number: ";
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.READ_PHONE_STATE }, 2);
        }

        mPhoneNumber += tMgr.getLine1Number();
        phoneNumber = tMgr.getLine1Number();

        textView_actualPhoneNumber.setText(mPhoneNumber);
    }

    public void btn_sendMessage_onClick(View v) {
        try {
            NetworkManager nm = new NetworkManager(phoneNumber);
            Peer destinationPeer = new Peer(editText_destinationPhoneNumber.getText().toString());
        }
        catch (Exception e) {
            Log.e("RuntimeException", Log.getStackTraceString(e));
        }
    }


}
