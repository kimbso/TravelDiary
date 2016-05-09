package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by roseanna on 5/4/16.
 */
public class ViewTraveledActivity extends Activity implements View.OnClickListener{

    TextView locationText, descriptionT;
    Button seePictures, done;
    Intent myIntent;
    Bundle myBundle;

    ListView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_traveled);

        locationText    = (TextView) findViewById(R.id.locationText);
        descriptionT    = (TextView) findViewById(R.id.description);

        seePictures     = (Button) findViewById(R.id.pictures);
        done            = (Button) findViewById(R.id.done);
        list            = (ListView) findViewById(R.id.favoritePlaces);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        Log.i("in view", "here");
        Location currentLocation = (Location) myBundle.get("ViewLocation");

        if(currentLocation != null) {
            Log.i("CURRENT LOCATION", currentLocation.getCity().getCity());
//            Consider deleting favorite places
//            ArrayList<String> placeArray = currentLocation.getFavoritePlaces();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, placeArray);
//            list.setAdapter(adapter);

            locationText.setText(currentLocation.getCity().getCity());
            descriptionT.setText(currentLocation.getDescription());
        }

        seePictures.setOnClickListener(this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, myIntent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pictures:
                Toast.makeText(this, "See pictures", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
