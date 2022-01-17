package com.nine.childcare.receivers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.views.act.HomeActivity;
import com.nine.childcare.views.act.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(Constants.ALARM_CONTENT)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(Constants.NOTIFICATION_ID, notification);
    }

}
