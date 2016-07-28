package ru.srtdevs.assusdan.weatherpaper;

import android.app.WallpaperManager;
import android.content.Context;
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


    public String getPaperUrl() { //TODO add code
        return "https://raw.githubusercontent.com/assusdan/weatherpaper_wallpaper_hub/master/dinner/clear/no/20/1.jpg";
    }

    public void showError(String err) {
        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
        toast.show();
    }


    public void setPaper(View view) throws Exception {

        downloadPaper();

        Bitmap bMap = BitmapFactory.decodeFile(this.getFilesDir() + "/paper.jpg");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        bMap = Bitmap.createScaledBitmap(bMap, size.x, size.y, false);


        WallpaperManager wallpaper = WallpaperManager.getInstance(this);
        wallpaper.setWallpaperOffsetSteps(1, 1);

        try {
            wallpaper.setBitmap(bMap);
        } catch (Exception e) {
            showError(e.toString());
        }

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
                outputStream = openFileOutput("paper.jpg", Context.MODE_PRIVATE);
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
