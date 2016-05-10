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

    TextView locationText, descriptionT, dates;
    Button seePictures, done;
    Intent myIntent;
    Bundle myBundle;

    ArrayList<String> picArray  = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_traveled);

        locationText    = (TextView) findViewById(R.id.locationText);
        descriptionT    = (TextView) findViewById(R.id.description);
        dates           = (TextView) findViewById(R.id.dates);

        seePictures     = (Button) findViewById(R.id.pictures);
        done            = (Button) findViewById(R.id.done);

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

            String city = currentLocation.getCity().getCity();
            String country = currentLocation.getCity().getCountry();
            picArray = currentLocation.getPictures();
            if (country.equals("")){
                locationText.setText(city);
            }
            else
                locationText.setText(city + ", " + country);
            descriptionT.setText(currentLocation.getDescription());
            dates.setText(currentLocation.getDates());
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
                seePictures();
                break;
            default:
                break;
        }
    }

    public void seePictures(){
        Intent intent = new Intent(ViewTraveledActivity.this, SeePicturesActivity.class);
        Bundle myBundle = new Bundle();
        myBundle.putString("picturePath1", picArray.get(0));
        myBundle.putString("picturePath2", picArray.get(1));
        myBundle.putString("picturePath3", picArray.get(2));
        myBundle.putString("picturePath4", picArray.get(3));
        myBundle.putString("picturePath5", picArray.get(4));
        intent.putExtras(myBundle);
        startActivityForResult(intent, 100);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100){
                Bundle myBundle = data.getExtras();
            }
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }

}
