package com.example.a127107.androidweartest;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int notificationId = 001; // A unique ID for our notification
    Button btnNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotif = (Button) this.findViewById(R.id.btnNotif);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 0,
                                intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action action = new
                        NotificationCompat.Action.Builder(
                        R.mipmap.ic_launcher,
                        getString(R.string.notification_title),
                        pendingIntent).build();

                Intent intentreply = new Intent(MainActivity.this,
                        ReplyActivity.class);
                PendingIntent pendingIntentReply = PendingIntent.getActivity
                        (MainActivity.this, 0, intentreply,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteInput ri = new RemoteInput.Builder("status")
                        .setLabel("Status report")
                        .setChoices(new String [] {"Done", "Not yet"})
                        .build();

                NotificationCompat.Action action2 = new
                        NotificationCompat.Action.Builder(
                        R.mipmap.ic_launcher,
                        "Reply",
                        pendingIntentReply)
                        .addRemoteInput(ri)
                        .build();

                NotificationCompat.WearableExtender extender = new
                        NotificationCompat.WearableExtender();
                extender.addAction(action);
                extender.addAction(action2);

                Notification notification = new
                        NotificationCompat.Builder(MainActivity.this)
                        .setContentText(getString(R.string.basic_notify_msg))
                        .setContentTitle(getText(R.string.notification_title))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        // When Wear notification is clicked, it performs
                        // the action we defined in line below
                        .extend(extender)
                        .build();

                NotificationManagerCompat notificationManagerCompat =
                        NotificationManagerCompat.from(MainActivity.this);
                notificationManagerCompat.notify(notificationId, notification);
            }
        });
    }
}
