package com.minkim.traveldiary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by roseanna on 4/28/16.
 */
public class AddTraveledActivity extends Activity {
    ListView list;
    
    CheckBoxAdapter adapter;
    Button add, delete, edit, view;
    ArrayList<Location> locationArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveled);

        final Intent intent = getIntent();

        list    = (ListView) findViewById(R.id.list);
        add     = (Button) findViewById(R.id.add);
        delete  = (Button) findViewById(R.id.delete);
        edit    = (Button) findViewById(R.id.edit);
        view    = (Button) findViewById(R.id.view);

        CityScrape cs = new CityScrape();
        cs.execute("Boston");

    }
    private class CityScrape extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(AddTraveledActivity.this);
        StringBuilder result = new StringBuilder();

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String apiKey = "AIzaSyAIXAmqOusAIK5-bUpYQrz837jXwbBQlTI";
            String jsonUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ params[0]
                    + "&types=(cities)&key=" + apiKey;
            Log.i("URL", jsonUrl);
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                JSONObject jObject      = new JSONObject(result.toString());
                JSONObject cityInfo     = jObject.getJSONObject("predictions");
                JSONArray prediction    = jObject.getJSONArray("predictions");

                Log.i("City info", cityInfo.toString());
                Log.i("Array info", prediction.toString());


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
            progressDialog.dismiss();
        }
    }
}
