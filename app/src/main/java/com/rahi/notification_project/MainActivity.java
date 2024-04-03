package com.rahi.notification_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "1" ;
    private final static String default_notification_channel_id = "default" ;
    Button btn;

    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        notificationManagerCompat = NotificationManagerCompat.from(this);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(view -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                Intent snoozeIntent = new Intent(MainActivity.this,MainActivity2.class);
                snoozeIntent.putExtra(NOTIFICATION_CHANNEL_ID,0);
                TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
                taskStackBuilder.addNextIntentWithParentStack(snoozeIntent);
                PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new NotificationCompat.Builder(this, default_notification_channel_id)
                        .setContentTitle("Hello").setContentText("This is RAhi PAtel").setSmallIcon(R.drawable.ic_baseline_looks_one_24)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setAutoCancel(true).setChannelId(NOTIFICATION_CHANNEL_ID).setVisibility(NotificationCompat.VISIBILITY_SECRET)
                        .setContentIntent(pendingIntent)
//                        .addAction(R.drawable.ic_android,"Android",pendingIntent)
                        .build();
                notificationManagerCompat.notify(1,notification);
            }
        });

    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is Sample Notification");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}