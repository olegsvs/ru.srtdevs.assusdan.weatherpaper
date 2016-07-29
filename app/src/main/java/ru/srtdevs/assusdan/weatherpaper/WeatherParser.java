package ru.srtdevs.assusdan.weatherpaper;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by assusdan on 29.07.16.
 */
public class WeatherParser {

    String weatherToken = "YOUR TOKEN";

    MainWorker parent;
    public String getFolder(String cityName){
        return parseWeather("http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+weatherToken+"&units=metric");
    }


    public WeatherParser(MainWorker parent){
        this.parent=parent;
    }

    public String parseWeather(String sUrl){

        InputStream input = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl);


            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "error";
            }

            input = connection.getInputStream();

            try {

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (true) {
                    int r = input.read(buffer);
                    if (r == -1) break;
                    out.write(buffer, 0, r);
                }

                return parseJson(out.toString());


            } catch (Exception e) {
                parent.showError(e.toString());
                parent.showError(connection.getResponseMessage());

            }


        } catch (Exception e) {

            parent.showError(e.toString());

        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
            return parseJson("error!");

        }



    }

    public String parseJson(String sJson){
        parent.showError(sJson);

        String folder = "";
        //add code
        return folder;
    }
}
