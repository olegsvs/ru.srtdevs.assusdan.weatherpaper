package ru.srtdevs.assusdan.weatherpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by assusdan on 28.07.16.
 */
public class MainWorker {

    BackgroundService parent;

    public void showError(String err) {
        Log.e("ERROR!", err);
    }

    public String getPaperUrl() { //TODO add code
        return "https://raw.githubusercontent.com/assusdan/weatherpaper_wallpaper_hub/master/dinner/clear/no/20/1.jpg";
    }

    public MainWorker (BackgroundService parent){

        this.parent = parent;

    }

    public void setPaper() throws Exception {
        Log.e("ALERT!", "Setting Paper...");
        downloadPaper();

        Bitmap bMap = BitmapFactory.decodeFile(parent.getFilesDir() + "/paper.jpg");
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

        showError("Wallpapers changed");


    }
    public void downloadPaper() throws Exception {
        String sUrl = getPaperUrl();
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

            try {

                outputStream = parent.openFileOutput("paper.jpg", Context.MODE_PRIVATE);
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
                showError(e.toString());
                showError(connection.getResponseMessage());

            }


        } catch (Exception e) {

            showError(e.toString());
            try {
                showError(connection.getResponseMessage());
            } catch (Exception ex) {
                showError("get contain failed");
            }
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
}
