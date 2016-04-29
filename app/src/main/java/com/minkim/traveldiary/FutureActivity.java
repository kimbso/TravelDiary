package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.*;

public class FutureActivity extends AppCompatActivity {

    ListView list;
    CheckBoxAdapter adapter;
    Button move, add, delete, edit, view, back;

    // For testing purposes
    ArrayList locations = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        final Intent intent = getIntent();

        list    = (ListView) findViewById(R.id.list);
        move    = (Button) findViewById(R.id.move);
        add     = (Button) findViewById(R.id.add);
        delete  = (Button) findViewById(R.id.delete);
        edit    = (Button) findViewById(R.id.edit);
        view    = (Button) findViewById(R.id.view);
        back    = (Button) findViewById(R.id.back);

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        // for testing purposes
        City city1 = new City("Rome", "Italy");
        City city2 = new City("Boston", "USA");
        ArrayList favPlaces1 = new ArrayList<String>();
        ArrayList favPlaces2 = new ArrayList<String>();
        ArrayList pics1 = new ArrayList<String>();
        ArrayList pics2 = new ArrayList<String>();
        Location test1 = new Location(city1, "nice place", favPlaces1, pics1);
        Location test2 = new Location(city2, "cool place", favPlaces2, pics2);
        locations.add(test1);
        locations.add(test2);

        adapter = new CheckBoxAdapter(this, locations);
        list.setAdapter(adapter);
    }
}
