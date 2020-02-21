package com.ehabahmed.studentcertificate;


import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

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
        if (remoteMessage.getNotification() != null) {
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
        setNotification();
    }

    private void setNotification() {
        Info info = (Info) getApplicationContext();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child("" + info.getId()).child("token");
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    reference.setValue("" + task.getResult().getToken());
                }
            }
        });
    }
}
