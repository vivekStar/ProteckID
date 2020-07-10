package com.webfarmatics.proteckapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.constraintlayout.solver.Cache;


public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private static Context mAppContext;
    public static boolean isAppOpen = false;
    private static AppController mInstance;

    @Override
    public void onCreate() {

        super.onCreate();
        mAppContext = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onActivityStarted(Activity activity) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                                                    Bundle outState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onActivityResumed(Activity activity) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onActivityPaused(Activity activity) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // TODO Auto-generated method stub
                isAppOpen = false;
            }

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                // TODO Auto-generated method stub

            }
        });
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppContext = null;
        isAppOpen = false;
    }

}
