package com.minkim.traveldiary;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    String cityName;
    String countryName;
    String searchName;

    EditText city, country;
    TextView locationName, info;
    Button search, weather, plane, add, done;

    SQLiteDatabase sampleDB;
    String tableName_future = "Future";

    Geocoder geocoder = null;
    private GoogleMap theMap;
    private LatLng latLng;
    double lat = 42.6556, lng = -70.6208;
    private LatLng myLocation;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        final Intent intent = getIntent();

        city = (EditText) findViewById(R.id.city);
        country = (EditText) findViewById(R.id.country);
        locationName = (TextView) findViewById(R.id.locationName);
        info = (TextView) findViewById(R.id.info);
        search = (Button) findViewById(R.id.search);
        weather = (Button) findViewById(R.id.weather);
//        plane        = (Button)   findViewById(R.id.plane);
        add = (Button) findViewById(R.id.add);
        done = (Button) findViewById(R.id.done);
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
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Couldn't create database");
        }
        search.setOnClickListener(this);
        weather.setOnClickListener(this);
        add.setOnClickListener(this);

        geocoder = new Geocoder(this);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100) {
            } else if (requestCode == 200) {
            }
        } catch (Exception e) {
            Log.i("ERROR", "onActivityResult");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public void searchClick() {
        cityName = city.getText().toString();
        countryName = country.getText().toString();
        if (countryName.equals("")) {
            locationName.setText(cityName);
            searchName = cityName.replace(" ", "_");
        } else if (cityName.equals("")) {
            locationName.setText(countryName);
            searchName = countryName.replace(" ", "_");
        } else{
            locationName.setText(cityName + ", " + countryName);
            searchName = cityName.replace(" ", "_") + ",_" + countryName.replace(" ", "_");
        }
        doClick();
        CityScrape cs = new CityScrape();
        cs.execute(searchName);
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
            String cVal = city.getText().toString();
            String coVal = country.getText().toString();
            String des = info.getText().toString();
            values.put("City", cVal);
            values.put("Country", coVal);
            values.put("Description", "Hello");
            Log.i("Insert Data", des);
            sampleDB.insert(tableName_future, null, values);
            Toast.makeText(this, "Activity added to Future Activities", Toast.LENGTH_SHORT).show();
        }
    }

    private void createTable() {
        Log.d(getLocalClassName(), "in create table");
        sampleDB.execSQL("Drop Table " + tableName_future);
        String query = "CREATE TABLE IF NOT EXISTS " + tableName_future +
                " (City VARCHAR, " +
                "  Country VARCHAR," +
                "  Description VARCHAR);";
        sampleDB.execSQL(query);
        Log.i("Created Table " + tableName_future, query);
    }

    private String cleanString(String result) {
        result = result.replace("\\n", "");
        String extracts = result.toString();
        String tag = "\"extract\":\"";
        String clean = "NONE";
        if (extracts.contains(tag)) {
            int index = extracts.indexOf(tag);
            int last = extracts.lastIndexOf("\"");
            extracts = extracts.substring(index + tag.length(), last);

            clean = android.text.Html.fromHtml(extracts).toString();
//            clean = removePar(clean);
            clean = firstSentence(clean);
            while (clean.contains("(") && clean.contains(")")) {
                int one = clean.indexOf("(") -1;
                int two = clean.lastIndexOf(")") + 1;
                String first = clean.substring(0, one);
                String end = clean.substring(two, clean.length() - 1);
                Log.i(first, end);
                return first.concat(end);
            }
        }
        return clean;
    }


    private String firstSentence(String text) {
        Log.i("text", text);
        if (text.contains(". ")) {
            int index = text.indexOf(". ");
            if (String.valueOf(text.charAt(index + 2)).equals(String.valueOf(text.charAt(index + 2)).toUpperCase()))
                return text.substring(0, index + 1);
            else
                return text.substring(0, index + 2) + firstSentence(text.substring(index + 2));
        }
        return text;
    }

    private void setDescription(String description) {
        if (description.equals("NONE"))
            Toast.makeText(this, "Try a different city", Toast.LENGTH_SHORT).show();
        else
            info.setText(description);
    }

    @Override
    public void onMapLoaded() {
        // code to run when the map has loaded

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5));
        theMap.setMyLocationEnabled(true);
        theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5));
        theMap.setMyLocationEnabled(true);
    }

    public void doClick() {
        Log.i(String.valueOf(lat), String.valueOf(lng));
        if (theMap == null)
            theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        latLng = new LatLng(lat, lng);
        // puts waterfall icon at location
        if(marker != null)
            marker.remove();
        marker = theMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(city.getText().toString()));
        theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5),
                3000, null);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.theMap = map;
        theMap.setOnMapLoadedCallback(this);      // calls onMapLoaded when layout done
        UiSettings mapSettings;
        mapSettings = theMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        theMap.setMyLocationEnabled(true);
    }

    private ArrayList getCoordinates(String text){
        ArrayList c = new ArrayList();
        String tag1 = "\"lat\":";
        String tag2 = "\"lon\":";
        String latC, lonC;
        if (text.contains(tag1) && text.contains(tag2)){
            latC = text.substring(text.indexOf(tag1)+tag1.length(), text.indexOf(tag2)-1);
            String sub = text.substring(text.indexOf(tag2));
            lonC = sub.substring(sub.indexOf(tag2)+tag2.length(), sub.indexOf(","));
            Log.i(lonC, latC);
            c.add(Double.valueOf(latC));
            c.add(Double.valueOf(lonC));
            return c;
        }
        return null;
    }
    private class CityScrape extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(DiscoverActivity.this);
        StringBuilder result        = new StringBuilder();
        StringBuilder coordResult   = new StringBuilder();
        String description;
        String location;

        ArrayList<Double> c         = new ArrayList<>();
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
            String coordURL = "https://en.wikipedia.org/w/api.php?action=query&prop=coordinates&format=json&titles=" + params[0];

            Log.i("URL", jsonUrl);
            Log.i("coordURL", coordURL);
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in                  = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader           = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }

                description = cleanString(result.toString());
                if (description == "NONE")
                    description = "Choose a different city";
                else
                    description = firstSentence(description);

                URL coords = new URL(coordURL);
                HttpURLConnection con = (HttpURLConnection) coords.openConnection();
                InputStream input     = new BufferedInputStream(con.getInputStream());
                BufferedReader read   = new BufferedReader(new InputStreamReader(input));

                String line2;
                while ((line2 = read.readLine()) != null){
                    coordResult.append(line2);
                }
                location = coordResult.toString();
                c = getCoordinates(location);
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
            if (c != null) {
                lat = c.get(0);
                lng = c.get(1);
                doClick();
            }
        }
    }

}
