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
    public BackgroundService() {
        super("myname");
        worker = new MainWorker(this);

    }

    public void showError (String e){
        Toast toast = Toast.makeText(getApplicationContext(), e, Toast.LENGTH_LONG);
        toast.show();    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int tm = 20;

        try {
            String city= intent.getExtras().getString("city");
            Log.e("THE CITY IS ", city);
           while(true){
            Log.w("ALERT!","START OF SERVICE");
            worker.setPaper(city);
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


}

