package prayhard.muck;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import java.util.Calendar;

public class MuckNotificationExec extends BroadcastReceiver{

    static private final String CHANNEL_ID          = "muck";
    static private final String CHANNEL_NAME        = "muckchannel";
    static private final String CHANNEL_DESCRIPTION = "notify users to their muck";

    //used for consistant intent storing
    static private final String KEY_TITLE = "keyt";
    static private final String KEY_BODY = "keyb";

    static private final long pattern[] = {0,200,125,400};


    //todo keys for canceling later, via deletion, editing of due date, or marking as 'done'

    @Override
    public void onReceive(Context activity, Intent intent) {

        //var
        String title;
        String body;

        Toast.makeText(activity, "RECEIVED", Toast.LENGTH_LONG).show();

        //assign
        title = intent.getStringExtra(KEY_TITLE);
        body = intent.getStringExtra(KEY_BODY);

        //create push notification
        push(activity, title, body);

        //todo update that the icon needs to change to red.

    }

    static public void createNotification(Context activity, Mish mish){

        //var
        String title;
        String body;
        long miliseconds;


        //assign
        title = mish.getTitle();
        body = mish.getBody();
        miliseconds = mish.getTimeToCompleteBy().getTimeInMillis();

        queue(activity, miliseconds, title, body);

    }

    static private void queue(Context activity, long miliseconds, String title, String body) {

        //complete guess here lmao

        //objects
        Intent alarmIntent;
        PendingIntent pendingIntent;
        AlarmManager alarmManager;


        //intent
        alarmIntent = new Intent(activity, MuckNotificationExec.class);
        alarmIntent.putExtra(KEY_TITLE, title);
        alarmIntent.putExtra(KEY_BODY, body);

        //pending intent
        pendingIntent = PendingIntent.getBroadcast(
                activity,
                0,
                alarmIntent,
                PendingIntent.FLAG_ONE_SHOT);

        //the alarm to wake the device
        alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);


        //create notification
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                miliseconds,
                pendingIntent);

    }

    static private void push(Context activity, String title, String body){

        //objects
        NotificationCompat.Builder builder;
        NotificationManager manager;
        SharedPreferences settings;
        Uri sound;
        Vibrator vibrator;

        //var
        String ring;

        //assign
        settings = PreferenceManager.getDefaultSharedPreferences(activity);

        //vibrate
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        //the visual data
        builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if(!body.equals(""))    //assign text only if available
            builder.setContentText(body);

        //sound
        ring = settings.getString("notifications_ringtone", "DEFAULT_SOUND");
        sound = Uri.parse(ring);
        if(settings.getBoolean("notifications_ring", true))
            builder.setSound(sound);


        //create manager
        manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);



        //send the notification
        manager.notify(001, builder.build());
        if(settings.getBoolean("notifications_vibrate", true))
            vibrator.vibrate(  pattern,-1);
    }

    static public void createNotificationChannel(ContextWrapper activity) {

        NotificationChannel channel;
        NotificationManager manager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //create chanel for newer sdk
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            manager = activity.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
