package com.ehabahmed.studentcertificate;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class StudentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,ServiecesForMessageAndNotification.class));
        Log.e("kkkkkkk", "aaaal");
    }
}