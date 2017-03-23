package com.example.demon.myapplication.ListViewsActivities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.demon.myapplication.R;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsFromWorldActivity extends AppCompatActivity {

    @BindView(R.id.newsFromWorldListView)
    ListView newsFromWorldListView;


    @BindView(R.id.checkNewsButton)
    Button checkNewsButton;

    private String urlSite;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_from_world);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.checkNewsButton)
    public void pressCheckNewsButton (View view) {
        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPreExecute() {
            progressBar = new ProgressDialog(NewsFromWorldActivity.this);
            progressBar.setMessage("Sciagam news api");
            progressBar.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return getData();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                progressBar.hide();
                NewsFromWorldActivity.this.parseAndShowJsonData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getData() throws IOException {
        urlSite = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=4d7b3f0765434fd38b80f39e69d8896d";
        URL url = new URL(urlSite);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } finally {
            urlConnection.disconnect();
        }

    }

    private String readStream(InputStream stream) throws IOException {
        return IOUtils.toString(stream, StandardCharsets.UTF_8.name());
    }

    private void parseAndShowJsonData(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return;
        }


        String parsedString ="";
        ArrayList<String> arrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray articlesArray = jsonObject.getJSONArray("articles");
        for (int i = 0; i<articlesArray.length(); i++) {
            arrayList.add(0, articlesArray.getJSONObject(i).getString("title"));
            parsedString +=articlesArray.getJSONObject(i).getString("author") + "\n";
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        newsFromWorldListView.setAdapter(arrayAdapter);



    }


}
