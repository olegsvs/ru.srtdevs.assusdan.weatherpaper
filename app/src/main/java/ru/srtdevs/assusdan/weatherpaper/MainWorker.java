package ru.srtdevs.assusdan.weatherpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by assusdan on 28.07.16.
 */

public class MainWorker {

    String filename = "";
    String dir = "";
    BackgroundService parent;
    WeatherParser weather;
    public void showError(String err) {
        parent.showError(err);
    }

    public String getPaperUrl(String city) throws Exception{ //TODO finish
        dir =  getTime() + weather.getFolder(city);
        filename =dir + "1.jpg";


        // URL example: https://raw.githubusercontent.com/assusdan/weatherpaper_wallpaper_hub/master/day/80/20.0/1.jpg

        return "https://raw.githubusercontent.com/assusdan/weatherpaper_wallpaper_hub/master/"+filename;
    }

    public MainWorker (BackgroundService parent){


        this.parent = parent;
        weather = new WeatherParser();
    }

    public void setPaper(String city) throws Exception {
        Log.e("ALERT!", "Setting Paper...");
        downloadPaper(city);

        Log.e("FILENAME", parent.getFilesDir() + "/" + filename);

        Bitmap bMap = BitmapFactory.decodeFile(parent.getFilesDir() + "/" + filename);
        DisplayMetrics metrics = parent.getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        bMap = Bitmap.createScaledBitmap(bMap, width, height, false);


        WallpaperManager wallpaper = WallpaperManager.getInstance(parent);
        wallpaper.setWallpaperOffsetSteps(1, 1);

        try {
            wallpaper.setBitmap(bMap);
        } catch (Exception e) {
            showError(e.toString());
        }

        Log.i("INFO","Wallpapers changed");


    }
    public void downloadPaper(String city) throws Exception {
        String sUrl = getPaperUrl(city);
        if (new File(parent.getApplicationContext().getFilesDir(), filename).exists()) {
            Log.e("DEBUG", "Wallpapers cached: "+filename);
            return;
        }
        Log.e("URL",sUrl);
        InputStream input = null;
        FileOutputStream outputStream = null;
        HttpURLConnection connection = null;
        URL url = new URL(sUrl);

            try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }

            input = connection.getInputStream();


                File dirs = new File (parent.getApplicationContext().getFilesDir(), dir);
                dirs.mkdirs();

                File picture = new File(parent.getApplicationContext().getFilesDir(), filename);

                outputStream = new FileOutputStream(picture);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (true) {
                    int r = input.read(buffer);
                    if (r == -1) break;
                    out.write(buffer, 0, r);
                }

                outputStream.write(out.toByteArray());
                outputStream.close();


            } catch (Exception e) {
                Log.e("Fatal", "connection error at line 110");
                e.printStackTrace();

            } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
    }

    public String getTime() { //TODO add code

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        Integer hours = Integer.parseInt(sdf.format(new Date()));

        Log.w("TIME",hours.toString());

        if ((hours>22)||(hours<07)){
            return "night";
        }
        if ((hours>=07)&&(hours<12)){
            return "morning";
        }
        if ((hours>=12)&&(hours<19)){
            return "day";
        }
        return "evening";

    }
}
