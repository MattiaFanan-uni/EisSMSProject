package com.gruppo3.smsconnection.smsdatalink.manager;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * NotificationEraser class
 *
 * @author Mattia Fanan
 */
public class NotificatonEraser extends NotificationListenerService {

    private static final char APP_ID = (char) 0x02;
    private static final String LOG_KEY = "SMS_HANDLER";

    /**
     * Overridden method that catches the notifications. If a messaging notification is
     * recognized and it contains the APP_ID than it will be cancelled.
     *
     * @param sbn StatusBarNotification object that contains all the notification informations.
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //  if(sbn.getPackageName().equals("com.google.android.apps.messaging")
        //          && sbn.getNotification().tickerText.toString().contains(APP_ID + ""))
        //  cancelNotification(sbn.getKey());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}