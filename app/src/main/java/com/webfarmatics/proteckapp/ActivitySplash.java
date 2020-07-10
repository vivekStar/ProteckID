package com.webfarmatics.proteckapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.GlobalData;

import java.util.Locale;


public class ActivitySplash extends AppCompatActivity {

    private Context context;
    private ImageView llSplash;
    private boolean internetAvailable = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.activity_splash);

        initialize();
        Locale locale1 = new Locale("in");
        Locale.setDefault(locale1);
        Configuration config = new Configuration();
        config.locale = locale1;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        checkInternetStatus();

    }


    private void initialize() {

        context = ActivitySplash.this;
        llSplash = findViewById(R.id.llSplash);

    }

    private void checkInternetStatus() {

        internetAvailable = GlobalData.checkConnection(context, llSplash);

        if (internetAvailable) {
            startThread();
        } else {
//            Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show();
            showInternetErrorDialog(context);
        }

    }

    private void startThread() {
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 40) {
            try {
                Thread.sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {

        String userId = CommonUtil.getSharePreferenceString(context, GlobalData.USER_ID, "0");
        int usId = Integer.parseInt(userId);
        if (usId != 0) {
            Intent intent = new Intent(ActivitySplash.this, ActivityRaiseIssue.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }

    }


    public void showInternetErrorDialog(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_no_internet);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((Button) dialog.findViewById(R.id.btnNetClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });

        ((Button) dialog.findViewById(R.id.btnNetRetry)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkInternetStatus();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }


}
