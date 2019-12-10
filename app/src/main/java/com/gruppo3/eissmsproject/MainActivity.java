package com.gruppo3.eissmsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gruppo3.smsconnection.replicatednet.manager.ReplicatedNetManager;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.StringSelfParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumberET;
    String bundleName = "netManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberET = findViewById(R.id.phoneNumberET);


    }

    //check if there is a valid phone number and call th net activity
    public void onPhoneNumberBtnClick(View view) {

        SMSPeer smsMe = null;
        ReplicatedNetPeer netMe = null;

        try {
            smsMe = new SMSPeer(phoneNumberET.getText().toString());
            netMe = new ReplicatedNetPeer(toSHA1(smsMe.getAddress().getBytes("UTF-8")));

            ReplicatedNetManager<String, String> netManager = new ReplicatedNetManager<>(netMe, smsMe, new StringSelfParser(),new StringSelfParser());
            Intent callNetActivityIntent = new Intent(this, NetActivity.class);
            callNetActivityIntent.putExtra(bundleName, netManager);

            startActivity(callNetActivityIntent);
        } catch (Exception e) {
        }
    }

    public byte[] toSHA1(byte[] convertMe) {
        String algorithm = "SHA-1";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] digest = md.digest(convertMe);
        byte[] rightLength = new byte[ReplicatedNetPeer.LENGTH];

        for (int i = 0; i < ReplicatedNetPeer.LENGTH; i++)
            rightLength[i] = digest[i];

        return rightLength;
    }
}
