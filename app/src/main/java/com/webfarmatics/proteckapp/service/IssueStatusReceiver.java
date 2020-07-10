package com.webfarmatics.proteckapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.utils.GlobalData;

public class IssueStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        GlobalData.toastMessage(context,"ProteckID Service Team");
        GlobalData.toastMessage(context,"Check your issue history.");
    }
}
