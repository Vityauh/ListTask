package com.example.demon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demon.myapplication.ListViewsActivities.NewsFromWorldActivity;
import com.example.demon.myapplication.ListViewsActivities.SportNewsActivity;
import com.example.demon.myapplication.ListViewsActivities.WeatherActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 :
                        Intent i = new Intent (MainActivity.this, WeatherActivity.class);
                        startActivity(i);
                        break;

                    case 1 :
                        i = new Intent(MainActivity.this, NewsFromWorldActivity.class);
                        startActivity(i);
                        break;

                    case 2 :
                        i = new Intent(MainActivity.this, SportNewsActivity.class);
                        startActivity(i);
                        break;
                }
            }

        });
    }

    public void listAdapter() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(0,"Weather");
        arrayList.add(1,"News from World");
        arrayList.add(2,"Sport News");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }



}

