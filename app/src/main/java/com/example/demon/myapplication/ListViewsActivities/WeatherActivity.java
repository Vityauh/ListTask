package com.example.demon.myapplication.ListViewsActivities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.demon.myapplication.R;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.cityEditText)
    EditText cityEditText;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.checkWeatherButton)
    Button checkWeatherButton;

    private String urlSite;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
    }

    @OnClick (R.id.checkWeatherButton)
    public void pressCheckWeatherButton (View view) {
        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(WeatherActivity.this);
            progressDialog.setMessage("Sciagam weather api");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return getData();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String data) {
            try {
                progressDialog.hide();
                WeatherActivity.this.parseAndShowJsonData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getData() throws IOException {
        String cityName = cityEditText.getText().toString();
        urlSite = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=af43a8eeca76b88dfca38412b4fe1275";
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

        JSONObject jsonObject = new JSONObject(data);
        String name = jsonObject.getString("name");

        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject sys = jsonObject.getJSONObject("sys");

        int temp = main.getInt("temp");
        long sunrise = sys.getLong("sunrise");
        long sunset = sys.getLong("sunset");

        DateTime sunriseDateTime = new DateTime(sunrise * 1000);
        DateTime sunsetDateTime = new DateTime(sunset * 1000);

        Period period = new Period(sunriseDateTime, sunsetDateTime);
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendHours().appendSeparator(":").appendMinutes().toFormatter();

        cityTextView.setText(name);
        temp = (int) (temp - 273.15);
        descriptionTextView.setText("Temp: "+temp + " " + "wschod: "+sunriseDateTime.toString("HH:mm") + " " +"zachod: "+ sunsetDateTime.toString("HH:mm") + " " +"dzien trwal: " +period.toString(formatter));
    }

}