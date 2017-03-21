package com.example.demon.myapplication.ListViewsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demon.myapplication.MainActivity;
import com.example.demon.myapplication.R;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toast.makeText(this, "weather", Toast.LENGTH_SHORT).show();
    }
}
