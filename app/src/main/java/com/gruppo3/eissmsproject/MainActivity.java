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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.NotificatonEraser;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements ReceivedMessageListener<SMSDataUnit> {

    private EditText txt_message;
    private EditText txt_phone_number;
    private SMSManager flHandler;
    private boolean canSend,canReceive,canRead;
    private AudioManager am ;
    private static final String NOT_VALID_PHONE_NUMBER = "Not a valid Phone  number";
    private static final String NOT_VALID_PAYLOAD = "Not a valid Payload";
    private static final String MESSAGE_SENT = "Message sent";
    private static final String SOMETHING_IS_MISSING = "Either phone number or message is missing";
    private static final String PERMISSION_DENIED = "Permission denied";
    private Ringtone ringtone;
    private static final String SILENT_MODE_OFF = "alto";
    private static final String SILENT_MODE_On = "basso";
    private static final String RING_KEY = "ring";

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
        SMSPayload data=null;

        if((!phoneNumber.equals("") && !message.equals(""))) {

            try{peer=new SMSPeer(phoneNumber);}
            catch(InvalidPeerException e){
                Toast.makeText(this, NOT_VALID_PHONE_NUMBER, Toast.LENGTH_SHORT).show();
            }

            try{data=new SMSPayload(message);}
            catch(InvalidPayloadException e){
                Toast.makeText(this, NOT_VALID_PAYLOAD, Toast.LENGTH_SHORT).show();
            }

            if(peer!=null&&data!=null) {
                SMSDataUnit sms = null;

                try{sms=new SMSDataUnit(new SMSHeader(peer,null),data);}
                catch (InvalidPeerException e){}
                catch (InvalidPayloadException e){}
                catch (InvalidHeaderException e){}

                if(sms!=null) {
                    flHandler.sendDataUnit(sms);

                    Toast.makeText(this, MESSAGE_SENT, Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(this, SOMETHING_IS_MISSING, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                }
                break;

            case 0:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canReceive=true;
                }
                else{
                    Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                }
                break;

            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canRead=true;
                }
                else{
                    Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * If the message received contains the keyword "alto" the silent mode is disabled, if it contains "basso" the silent mode is enabled
     * If it contains the keyword "ring" the silent mode is disabled and the phone rings for 5 seconds
     * @param message The message received
     */
    @Override
    public void onMessageReceived(SMSDataUnit message) {
        TextView txtReceive=(TextView) findViewById(R.id.txt_message2);
        String text=message.getPayload().getData();
        txtReceive.setText(message.toString());

        if(text.contains(SILENT_MODE_OFF))
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        else if(text.contains(SILENT_MODE_On)) {

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
        else if (text.contains(RING_KEY)){
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            AudioManager audioMan = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioMan.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), alert);
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
            ringtone.play();
            CountDownTimer timeToStopAlarm = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    ringtone.stop();
                }
            }.start();
        }
    }
}
