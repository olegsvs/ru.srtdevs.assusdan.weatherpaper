package ru.srtdevs.assusdan.weatherpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onBtnClick(View view) {
        try {

            Intent service = new Intent(this, BackgroundService.class);
            showError("Intent started 1");
            startService(service.putExtra("time", 10));


            /*
                MainWorker worker = new MainWorker(this);
                worker.setPaper();
            */



        } catch (Exception e) {
            showError(e.toString());
        }
    }


    public void showError(String err) {
        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
        toast.show();
    }



}
