package com.minkim.traveldiary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

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

public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener{

    String cityName;
    String countryName;

    EditText city, country;
    TextView locationName, info;
    Button search, weather, plane, add, done;

    SQLiteDatabase sampleDB;
    String tableName_future  = "Future";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        final Intent intent = getIntent();

        city         = (EditText) findViewById(R.id.city);
        country      = (EditText) findViewById(R.id.country);
        locationName = (TextView) findViewById(R.id.locationName);
        info         = (TextView) findViewById(R.id.info);
        search       = (Button)   findViewById(R.id.search);
        weather      = (Button)   findViewById(R.id.weather);
        plane        = (Button)   findViewById(R.id.plane);
        add          = (Button)   findViewById(R.id.add);
        done         = (Button)   findViewById(R.id.done);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                myBundle.putString("task", "back");
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        try {
            sampleDB = openOrCreateDatabase("TravelDiary", MODE_PRIVATE, null);
            createTable();
        } catch (SQLiteException se){
            Log.e(getClass().getSimpleName(), "Couldn't create database");
        }
        search.setOnClickListener(this);
        weather.setOnClickListener(this);
        add.setOnClickListener(this);

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100){
            }
            else if (requestCode == 200){
            }
        }
        catch (Exception e){
            Log.i("ERROR","onActivityResult");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                addClick();
                break;
            case R.id.weather:
                weatherClick();
                break;
            case R.id.search:
                searchClick();
                break;
        }
    }

    public void searchClick(){
        cityName = city.getText().toString();
        countryName = country.getText().toString();
        if (countryName.equals("")) {
            locationName.setText(cityName);
        }
        else {
            locationName.setText(cityName + ", " + countryName);
        }
        CityScrape cs = new CityScrape();
        cs.execute(cityName);
    }
    public void weatherClick() {
        Intent intent = new Intent(DiscoverActivity.this, WeatherActivity.class);
        cityName = city.getText().toString();
        countryName = country.getText().toString();
        if (!cityName.equals("")) {
            Bundle myData = new Bundle();
            myData.putString("cityName", cityName);
            myData.putString("countryName", countryName);
            intent.putExtras(myData);
            startActivityForResult(intent, 100);
        }
    }

    public void addClick() {
        if (city.getText().length() == 0)
            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
        else {
            ContentValues values = new ContentValues();
            String cVal     = city.getText().toString();
            String coVal    = country.getText().toString();
            String des      = info.getText().toString();
            values.put("City", cVal);
            values.put("Country", coVal);
            values.put("Description", "Hello");
            Log.i("Insert Data", des);
            sampleDB.insert(tableName_future, null, values);
//            String query = "Insert into " + tableName_future + "(City, Country, Description)" +
//                    "values (" + cVal + ", " + coVal + ", " + des + ");";
//            sampleDB.execSQL(query);
//            Log.i("query", query)
            Toast.makeText(this, "Activity added to Future Activities", Toast.LENGTH_SHORT).show();
        }
    }

    private void createTable() {
        Log.d(getLocalClassName(), "in create table");
        String query = "CREATE TABLE IF NOT EXISTS " + tableName_future +
                " (City VARCHAR, " +
                "  Country VARCHAR," +
                "  Description VARCHAR);";
        sampleDB.execSQL(query);
        Log.i("Created Table " + tableName_future, query);
    }

    private String cleanString(String result){
        String extracts = result.toString();
        String tag = "\"extract\":\"";
        String clean = "NONE";
        if (extracts.contains(tag)) {
            int index = extracts.indexOf(tag);
            int last = extracts.lastIndexOf("\"");
            extracts = extracts.substring(index + tag.length(), last);

            clean = android.text.Html.fromHtml(extracts).toString();
            while (clean.contains("(") && clean.contains(")")){
                int one = clean.indexOf("(");
                int two = clean.indexOf(")") + 1;
                String first = clean.substring(0,one);
                String end   = clean.substring(two, clean.length()-1);
                return first.concat(end);
            }
        }
        return clean;
    }

    private String firstSentence(String text){
        int index = text.indexOf(". ");
        if (String.valueOf(text.charAt(index+2)).equals(String.valueOf(text.charAt(index+2)).toUpperCase()))
            return text.substring(0, index+1);
        else
            return text.substring(0, index+2) + firstSentence(text.substring(index+2));
    }

    private void setDescription(String description){
        if (description.equals("NONE"))
            Toast.makeText(this, "Try a different city", Toast.LENGTH_SHORT).show();
        else
            info.setText(description);
    }

    private class CityScrape extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(DiscoverActivity.this);
        StringBuilder result = new StringBuilder();
        String description;
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
//            String apiKey = "AIzaSyAIXAmqOusAIK5-bUpYQrz837jXwbBQlTI";
//            String jsonUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ params[0]
//                    + "&types=(cities)&key=" + apiKey;
            String jsonUrl = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&exintro&format=json&titles=" + params[0];
            Log.i("URL", jsonUrl);
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null){
                    Log.i("line", line);
                    result.append(line);
                }
                description = cleanString(result.toString());
                if (description == "NONE"){
                    Log.i("no extract", "fuck");
                }else {
                    description = firstSentence(description);
                    Log.i("description", description);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){
            progressDialog.dismiss();
            setDescription(description);
        }
    }

}
