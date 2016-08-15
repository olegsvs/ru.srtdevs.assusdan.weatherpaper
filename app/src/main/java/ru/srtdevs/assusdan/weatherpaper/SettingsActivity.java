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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.editText = (EditText)findViewById(R.id.editText);
    }

    public void onBtnClick(View view) {
        try {

            Intent service = new Intent(this, BackgroundService.class);
            showError("Intent started 1");

            startService(service.putExtra("city", this.editText.getText().toString()));


        } catch (Exception e) {
            showError(e.toString());
        }
    }


    public void showError(String err) {
        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
        toast.show();
    }



}
