package ru.srtdevs.assusdan.weatherpaper;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class SettingsActivity extends AppCompatActivity {
    EditText editText = null;
    Intent service = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        service = new Intent(this, BackgroundService.class);

        this.editText = (EditText)findViewById(R.id.editText);
    }

    public void toStartService(View view) {
        try {
            startService(service.putExtra("city", this.editText.getText().toString()));
        } catch (Exception e) {
            showError(e.toString());
        }
    }


    public void toStopService(View view) {
        try {
            stopService(service.putExtra("city", this.editText.getText().toString()));
        } catch (Exception e) {
            showError(e.toString());
        }
    }


    public void showError(String err) {
        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
        toast.show();
    }



}
