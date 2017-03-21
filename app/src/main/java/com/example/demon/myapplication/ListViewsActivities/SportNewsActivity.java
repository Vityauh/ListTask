package com.example.demon.myapplication.ListViewsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demon.myapplication.R;

public class SportNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "sport", Toast.LENGTH_SHORT).show();
    }
}
