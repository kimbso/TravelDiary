package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class WeatherActivity extends AppCompatActivity {

    TextView locationName;
    ListView list;
    Button done;

    String cityName;

    private ArrayList<Weather> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        days = new ArrayList<>();

        final Intent intent = getIntent();

        Bundle myBundle =  intent.getExtras();
        cityName = myBundle.getString("cityName");

        locationName = (TextView) findViewById(R.id.locationName);
        list         = (ListView) findViewById(R.id.list);
        done         = (Button)   findViewById(R.id.done);
        locationName.setText(cityName);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Scraper ws = new Scraper();
        ws.execute(cityName);
    }

    public void update(){
        WeatherAdapter wa = new WeatherAdapter(this, days);
        list.setAdapter(wa);
    }

    private class Scraper extends AsyncTask<String, String, String>{
        StringBuilder result = new StringBuilder();
        protected void onPreExecute() {
            if (!days.isEmpty())
                days.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            String jsonUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q="
                    + params[0]
                    + "&appid=67083ad4da658a89755054ad0bbd81fd&units=imperial";
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null)
                    result.append(line);
                JSONObject j = new JSONObject(result.toString());
                JSONObject inf = j.getJSONObject("city");
                JSONArray array = j.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject day = array.getJSONObject(i);
                    Weather wd = new Weather(inf, day);
                    days.add(wd);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){
            Weather tester = days.get(0);
            String name = tester.getCity();
            boolean same = name.toLowerCase().equals(cityName.toLowerCase());
            if (!same)
                days.clear();
            update();
        }
    }
}
