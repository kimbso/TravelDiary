package com.minkim.traveldiary;

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

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoverActivity.this, WeatherActivity.class);
                cityName = city.getText().toString();
                countryName = country.getText().toString();
                if(!cityName.equals("")){
                    Bundle myData = new Bundle();
                    myData.putString("cityName", cityName);
                    myData.putString("countryName", countryName);
                    intent.putExtras(myData);
                    startActivityForResult(intent, 100);
                }
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

        }
    }

    public void addClick(){
        String newCity = city.getText().toString();
        String newCountry = country.getText().toString();
        City addCity = new City(newCity, newCountry);
        Location newLocation = new Location(addCity);
        Toast.makeText(this, "Activity added to Future Activities", Toast.LENGTH_SHORT);
    }
}
