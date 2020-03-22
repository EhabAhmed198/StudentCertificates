package com.ehabahmed.studentcertificate;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.NotificationCompat;

public final class StudentNotification  {

    protected static void showNotification(final Context context
            , final int NOTIFICATION_ID
            , final String NOTIFICATION_CHANNEL_ID
            , final PendingIntent PENDING_INTENT
            , final String title, final String text, final String bigText

            ) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "primary", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                        .setContentIntent(PENDING_INTENT)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true);

                    notification.setPriority(NotificationCompat.PRIORITY_HIGH);
                notificationManager.notify(NOTIFICATION_ID, notification.build());

            }
        },0);

    }


}
