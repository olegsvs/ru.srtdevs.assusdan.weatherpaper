package ru.srtdevs.assusdan.weatherpaper;

/**
 * Created by assusdan on 28.07.16.
 */

import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends IntentService {
    MainWorker worker;

    public BackgroundService() {
        super("myname");
        worker = new MainWorker(this);

    }

    public void showError(String e) {
        Toast toast = Toast.makeText(getApplicationContext(), e, Toast.LENGTH_LONG);
        toast.show();
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int tm = 60;

        try {
            Notification notif = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setContentTitle("I'M IMMORTAL")
                    .setContentText("Now have fun))0)")
                    .build();
            startForeground(1, notif);
            String city = intent.getExtras().getString("city");
            while (true) {
                Log.w("ALERT!", "START OF SERVICE");
                worker.setPaper(city);
                Runtime.getRuntime().gc();
                TimeUnit.SECONDS.sleep(tm);
            }

        } catch (Exception e) {
            stopForeground(true);
            e.printStackTrace();
            Log.e("SUSLOV_DEBUG", e.toString());
        }
    }

    public void onDestroy() {
        worker.showError("I'M DEAD");

        super.onDestroy();
    }


}

