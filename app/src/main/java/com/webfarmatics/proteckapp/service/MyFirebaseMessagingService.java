package com.webfarmatics.proteckapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.webfarmatics.proteckapp.ActivityHistory;
import com.webfarmatics.proteckapp.ActivityLogin;
import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.Config;
import com.webfarmatics.proteckapp.utils.GlobalData;

import java.util.Calendar;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {

        super.onNewToken(token);

        Log.e("token", token);

        // Saving reg id to shared preferences
        storeRegIdInPref(token);

        // sending reg id to your server
        sendRegistrationToServer(token);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        CommonUtil.setSharePreferenceString(getApplicationContext(), GlobalData.NOTIFICATION_STATUS, GlobalData.NOTIFICATION_UNSEEN);

        Intent intent = null;
        String userId = CommonUtil.getSharePreferenceString(getApplicationContext(), GlobalData.USER_ID, "0");
        if (userId.equalsIgnoreCase("0")) {
            intent = new Intent(this, ActivityLogin.class);
        } else {
            intent = new Intent(this, ActivityHistory.class);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(),
                intent, PendingIntent.FLAG_ONE_SHOT);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setSound(alarmSound)
                        .setAutoCancel(true)
                        .setContentTitle("ProteckID Service Team.")
                        .setContentText("Your issue details were received. We update you soon.");

// Gets an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//to post your notification to the notification bar
        notificationManager.notify(0, mBuilder.build());

        Intent intentNotify = new Intent();
        intentNotify.setAction(GlobalData.CUSTOM_INTENT_ACTION);
        sendBroadcast(intentNotify);


    }


    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
    }

    private void storeRegIdInPref(String token) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(GlobalData.USER_TOKEN, token);
        editor.apply();
        editor.commit();
    }


}
