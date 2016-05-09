package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class AddFutureActivity extends Activity implements View.OnClickListener {

    EditText cityText, countryText, description;
    Button done;
    Location newLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_future);

        description = (EditText) findViewById(R.id.description);
        cityText    = (EditText) findViewById(R.id.cityText);
        countryText = (EditText) findViewById(R.id.countryText);
        done        = (Button)   findViewById(R.id.done);

        description.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done:
                done();
                break;
            default:
                break;
        }
    }

    public void populateLocation(){
        String city = cityText.getText().toString();
        String country = countryText.getText().toString();

        City newCity = new City(city, country);
        newLocation = new Location(newCity, description.getText().toString(), null, null);
    }
    public void done() {
        if(!cityText.getText().toString().equals("")) {
            populateLocation();
            final Intent myIntent = getIntent();
            Bundle myBundle = new Bundle();
            myBundle.putSerializable("Location", newLocation);
            myIntent.putExtras(myBundle);
            setResult(Activity.RESULT_OK, myIntent);
            finish();
        }
        else
            Toast.makeText(this, "Please enter a city", Toast.LENGTH_SHORT).show();
    }
}
