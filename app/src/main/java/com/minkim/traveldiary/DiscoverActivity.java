package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DiscoverActivity extends AppCompatActivity {

    EditText city, country;
    TextView info;
    Button search, weather, plane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        final Intent intent = getIntent();

        city    = (EditText) findViewById(R.id.city);
        country = (EditText) findViewById(R.id.country);
        info    = (TextView) findViewById(R.id.info);
        search  = (Button)   findViewById(R.id.search);
        weather = (Button)   findViewById(R.id.weather);
        plane   = (Button)   findViewById(R.id.plane);
    }
}
