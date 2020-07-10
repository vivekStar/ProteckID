package com.webfarmatics.proteckapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class GlobalData {

    public static final String ATTACHMENT_PATH = "http://188.95.36.104:8080/ProteckCenter/resources/attachment/";

    public static String RESPONSE_CODE = "CODE";
    public static String RESPONSE_CODE_200 = "200";
    public static String RESPONSE_CODE_400 = "400";
    public static String RESPONSE_CODE_500 = "500";
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    public static final String NOTIFICATION_STATUS = "NOTIFICATION_STATUS";
    public static final String NOTIFICATION_SEEN = "NOTIFICATION_SEEN";
    public static final String NOTIFICATION_UNSEEN = "NOTIFICATION_UNSEEN";

    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String API_KEY = "abcd";
    public static final String CUSTOM_INTENT_ACTION = "com.webfarmatics.proteckapp.CUSTOM_INTENT";

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";

    public static final String USER_ID = "USER_ID";

    public static int REQUEST_PROFILE_UPDATE = 1005;

    private static ProgressDialog pDialog;

    public static void setProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }


    public static void showDialog() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    public static void hideDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();

    }


    public static void toastMessage(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }


    // Method to manually check connection status
    public static boolean checkConnection(Context context, View view) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
//        showSnack(haveConnectedWifi || haveConnectedMobile, view);

        return haveConnectedWifi || haveConnectedMobile;
    }

}
