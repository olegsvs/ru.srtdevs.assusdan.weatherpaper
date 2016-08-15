package ru.srtdevs.assusdan.weatherpaper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by assusdan on 29.07.16.
 */
public class WeatherParser {

    String weatherToken = "YOUR TOKEN";

    public String getFolder(String cityName) throws  Exception{
        return parseWeather("http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+weatherToken+"&units=metric");
    }


    public WeatherParser(){
    }

    public String parseWeather(String sUrl) throws Exception{
        StringBuffer buffer = new StringBuffer();

        InputStream is = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(sUrl);


            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

// Let's read the response
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();






        } catch (Exception e) {

            Log.e("Conn.error",e.toString());

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ignored) {
            }

            if (con != null)
                con.disconnect();

        }

        return parseJson(buffer.toString());


    }

    public String parseJson(String strJson) throws Exception{

        Log.e("RECIEVED JSON",strJson);

        String folder = "/";
        String rescity = "";

        JSONObject dataJsonObj = null;

        try {
            dataJsonObj = new JSONObject(new JSONTokener(strJson));

            Double temp = dataJsonObj.getJSONObject("main").getDouble("temp");

            folder += dataJsonObj.getJSONArray("weather").getJSONObject(0)
                    .getString("icon").substring(0,2)
                    + "/" + String.valueOf(temp - (temp % 10)) + "/";

            rescity = dataJsonObj.getString("name");


        } catch (JSONException e) {
            Log.e("ERROR" ,e.toString());
       }

        Log.e("CITY 2 IS",rescity);

        return folder;
    }
}
