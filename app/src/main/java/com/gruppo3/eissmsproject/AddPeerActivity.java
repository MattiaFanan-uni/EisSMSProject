package com.gruppo3.eissmsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gruppo3.smsconnection.replicatednet.manager.ReplicatedNetManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

public class AddPeerActivity extends AppCompatActivity {

    ReplicatedNetManager<String, String> netManager;
    EditText invitedPeerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peer);
        invitedPeerEditText = findViewById(R.id.invitedPeerEditText);

        netManager = ReplicatedNetManager.getDefault();
    }

    public void onAddPeerButtonClick(View view) {

        try {
            SMSPeer smsPeerToInvite = new SMSPeer(invitedPeerEditText.getText().toString());
            netManager.invite(smsPeerToInvite);

            Intent callNetActivity = new Intent(this, NetActivity.class);
            startActivity(callNetActivity);
        } catch (Exception e) {
        }
    }
}
