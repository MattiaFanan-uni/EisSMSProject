package com.gruppo3.eissmsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.NotificatonEraser;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;

public class MainActivity extends AppCompatActivity implements ReceivedMessageListener<SMSDataUnit> {

    private EditText txt_message;
    private EditText txt_phone_number;
    private SMSManager flHandler;
    private boolean canSend,canReceive,canRead;
    private AudioManager am ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkNotificationListenerPermission()){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }

        canReceive=false;
        canReceive=false;
        canSend=false;

        am= (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        txt_message = (EditText) findViewById(R.id.txt_message);
        txt_phone_number = (EditText) findViewById(R.id.txt_phone_number);

        flHandler= SMSManager.getDefault();
        flHandler.addReceiveListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS},6);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS},5);
        }


    }

    private boolean checkNotificationListenerPermission() {
        ComponentName cn = new ComponentName(this, NotificatonEraser.class);
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        final boolean enabled = flat != null && flat.contains(cn.flattenToString());
        return enabled;
    }

    public void btn_send(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
            canSend=true;
            MyMessage();
        }

        else {
            canSend=false;
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},3);
        }
    }



    private void MyMessage() {
        String phoneNumber = txt_phone_number.getText().toString().trim();
        String message = txt_message.getText().toString().trim();

        SMSPeer peer=null;
        SMSMessage data=null;

        if((!phoneNumber.equals("") && !message.equals(""))) {

            try{peer=new SMSPeer(phoneNumber);}
            catch(InvalidPeerException e){
                Toast.makeText(this, "Not A Valid Phone Number", Toast.LENGTH_SHORT).show();
            }

            try{data=new SMSMessage(message);}
            catch(InvalidDataException e){
                Toast.makeText(this, "Not A Valid Payload", Toast.LENGTH_SHORT).show();
            }

            if(peer!=null&&data!=null) {

                SMSDataUnit sms = null;

                try{sms=new SMSDataUnit(peer,data);}
                catch (InvalidPeerException e){}
                catch (InvalidDataException e){}

                if(sms!=null) {
                    flHandler.sendDataUnit(sms);

                    Toast.makeText(this, "Messagio inviato", Toast.LENGTH_SHORT).show();
                }

            }
        }

        else {
            Toast.makeText(this, "manca il numero o il messaggio", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canReceive=true;
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canRead=true;
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onMessageReceived(SMSDataUnit message) {
        TextView txtReceive=(TextView) findViewById(R.id.txt_message2);
        String text=message.getMessage().getData();
        txtReceive.setText(message.toString()+" "+text);
        if(text.contains("alto"))
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        else if(text.contains("basso")) {

            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !notificationManager.isNotificationPolicyAccessGranted()) {

                Intent intent = new Intent(
                        android.provider.Settings
                                .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                startActivity(intent);
            }
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
}
