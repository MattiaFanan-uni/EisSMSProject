package com.gruppo3.eissmsproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gruppo3.smsconnection.replicatednet.manager.ReplicatedNetManager;

public class AddResourceActivity extends AppCompatActivity {

    ReplicatedNetManager<String, String> netManager;
    EditText keyEditText;
    EditText valueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        netManager = ReplicatedNetManager.getDefault();

        keyEditText = findViewById(R.id.keyEditText);
        valueEditText = findViewById(R.id.valueEditText);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 6);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 5);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 5);
        }

    }

    public void onInsertResourceButtonClick(View view) {
        insert(keyEditText.getText().toString(), valueEditText.getText().toString());

        Intent callNetActivityIntent = new Intent(this, NetActivity.class);
        startActivity(callNetActivityIntent);
    }

    private void insert(String key, String value) {

        String command=netManager.getDictionary().getAddResourceCommand(key,value);
        netManager.getDictionary().getResourceCommandExecutor().execute(netManager.getDictionary(),command);
        netManager.broadcast(command);

    }
}
