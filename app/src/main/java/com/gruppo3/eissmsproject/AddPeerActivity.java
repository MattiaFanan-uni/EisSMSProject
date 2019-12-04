package com.gruppo3.eissmsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.gruppo3.smsconnection.replicatednet.manager.ReplicatedNetManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

public class AddPeerActivity extends AppCompatActivity {

    ReplicatedNetManager<String,String> netManager;
    String bundleName="netManager";
    EditText invitedPeerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peer);
        invitedPeerEditText=findViewById(R.id.invitedPeerEditText);

        Intent startingIntent=getIntent();
        netManager=(ReplicatedNetManager<String, String>) startingIntent.getExtras().get(bundleName);
    }

    public void onAddPeerButtonClick(View view) {

        try {
            SMSPeer smsPeerToInvite=new SMSPeer(invitedPeerEditText.getText().toString());
            netManager.invite(smsPeerToInvite);

            Intent callNetActivity = new Intent(this, NetActivity.class);
            callNetActivity.putExtra(bundleName, netManager);
            startActivity(callNetActivity);
        }
        catch (Exception e){}
    }
}
