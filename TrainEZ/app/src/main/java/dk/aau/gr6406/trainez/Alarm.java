package dk.aau.gr6406.trainez;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import  dk.aau.gr6406.trainez.EnterWeightActivity;


/**
 * Created by marti on 4/16/2017.
 */

public class Alarm extends BroadcastReceiver{
    private static final String TAG ="dk.aau.studiegruppe" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"On receive in AlertRecieve");

        createNotification(context, "Train EZ","Don't forget to enter your weight", "Reminder");
    }

    private void createNotification(Context context, String msg, String msgText, String msgAlert) {

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0 , new Intent(context, EnterWeightActivity.class),0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1,mBuilder.build());

    }
}