package com.minkim.traveldiary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by roseanna on 4/28/16.
 */
public class AddTraveledActivity extends Activity implements View.OnClickListener{
    EditText cityText, countryText, favoritePlaces, descriptionT;
    Button done, dates, addPictures;
    public Location newLocation;
    public City newCity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_traveled);

        cityText        = (EditText) findViewById(R.id.cityText);
        countryText     = (EditText) findViewById(R.id.countryText);
        favoritePlaces  = (EditText) findViewById(R.id.favoritePlaces);
        descriptionT    = (EditText) findViewById(R.id.description);
        done            = (Button) findViewById(R.id.done);
        dates           = (Button) findViewById(R.id.dates);
        addPictures     = (Button) findViewById(R.id.pictures);

        done.setOnClickListener(this);
        dates.setOnClickListener(this);
        addPictures.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done:
                doneClick();
                break;
            case R.id.dates:
                dateClick();
                break;
            case R.id.pictures:
                pictureClick();
                break;
            default:
                break;
        }
    }

    // Get location information
    public void doneClick(){
        String city     = cityText.getText().toString();
        Log.i("City", city);
        String country  = countryText.getText().toString();
        putLocation();

//        CityScrape cs = new CityScrape();
//        cs.execute(city);
    }

    public void populateLocation(){
        String city = cityText.getText().toString();
        String country = countryText.getText().toString();
        newCity = new City(city, country);
        String description = descriptionT.getText().toString();
        ArrayList<String> picArray = new ArrayList<>();
        ArrayList<String> favPlaces = new ArrayList<>();
        favPlaces.add(favoritePlaces.getText().toString());
        newLocation = new Location(newCity, description, favPlaces, picArray);
    }
    public void putLocation(){
        populateLocation();
        Log.i("populate", newLocation.getCity().getCity());
        final Intent myIntent = getIntent();
        Bundle myBundle = new Bundle();
        myBundle.putSerializable("Location", newLocation);
        myIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }

    public void dateClick(){

    }

    public void pictureClick(){

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
                JSONArray prediction    = jObject.getJSONArray("predictions");
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
            putLocation();
        }
    }
}
