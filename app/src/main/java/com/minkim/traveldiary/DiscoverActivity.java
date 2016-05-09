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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = city.getText().toString();
                countryName = country.getText().toString();
                locationName.setText(cityName + ", " + countryName);
            }
        });

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
        }
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
            values.put("City", city.getText().toString());
            values.put("Country", country.getText().toString());
            Log.i("Insert Data", city.getText().toString());
            sampleDB.insert(tableName_future, null, values);
            Toast.makeText(this, "Activity added to Future Activities", Toast.LENGTH_SHORT).show();
        }
    }

    private void createTable() {
        Log.d(getLocalClassName(), "in create table");
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName_future +
                " (City VARCHAR, " +
                "  Country VARCHAR);");
        Log.i("Created Table " + tableName_future, "Done");

    }
}
