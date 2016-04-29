package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class DiscoverActivity extends AppCompatActivity {

    String cityName;
    String countryName;

    EditText city, country;
    TextView locationName, info;
    Button search, weather, plane;

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
                Bundle myData = new Bundle();
                myData.putString("cityName", cityName);
                myData.putString("countryName", countryName);
                intent.putExtras(myData);
                startActivityForResult(intent, 100);
            }
        });
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
}
