package com.example.demon.myapplication.ListViewsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demon.myapplication.R;

public class NewsFromWorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_from_world);
        Toast.makeText(this, "news", Toast.LENGTH_SHORT).show();
    }
}
