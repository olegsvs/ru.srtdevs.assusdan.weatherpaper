package ru.srtdevs.assusdan.weatherpaper;

/**
 * Created by assusdan on 28.07.16.
 */

import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends IntentService {

    MainWorker worker;
    public BackgroundService(SettingsActivity parent) {
        super("myname");
        worker = new MainWorker(this);

    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int tm = 20;

        try {
           while(true){
            Log.w("ALERT!","START OF SERVICE");
            worker.setPaper();
            TimeUnit.SECONDS.sleep(tm);
            worker.showError("END OF SERVICE");
           }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SUSLOV_DEBUG",e.toString());
        }
    }

    public void onDestroy() {
        worker.showError("I'M DEAD");

        super.onDestroy();
    }

    public void showError(String err){
       // Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
       // toast.show();
    }
}

