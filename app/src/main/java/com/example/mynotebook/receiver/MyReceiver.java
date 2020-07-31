package com.example.mynotebook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mynotebook.utility.Utils;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("xm", "onReceive");
        Utils.toast("receive open");

    }
}
