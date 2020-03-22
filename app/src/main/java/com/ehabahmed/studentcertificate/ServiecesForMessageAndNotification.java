package com.ehabahmed.studentcertificate;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class ServiecesForMessageAndNotification extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ServiecesForMessageAndNotification.this, "klja", Toast.LENGTH_SHORT).show();
            }
        },0);
        if (remoteMessage.getNotification() != null) {
           Log.e("Log","firebasw.....");
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Intent intent = new Intent(this, Inbox.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 10210, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            StudentNotification.showNotification(this, 10210, "notification_message", pendingIntent, title, body, "New Message");
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        setNotification(s);
    }

    private void setNotification(String s) {
        Info info = (Info) getApplicationContext();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child("" + info.getId()).child("token");
        reference.setValue(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
