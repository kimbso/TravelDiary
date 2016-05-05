package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by roseanna on 5/4/16.
 */
public class EditTraveledActivity extends Activity  implements View.OnClickListener{
    EditText cityText, countryText, favoritePlaces, descriptionT;
    Button done, dates, addPictures;
    Intent myIntent;
    Bundle myBundle;

    int index;

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

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        Location oldLocation = (Location) myBundle.get("oldLocation");

        cityText.setText(oldLocation.getCity().getCity());
        countryText.setText(oldLocation.getCity().getCountry());
        favoritePlaces.setText(oldLocation.getFavoritePlaces().toString());
        descriptionT.setText(oldLocation.getDescription());

        done.setOnClickListener(this);
        dates.setOnClickListener(this);
        addPictures.setOnClickListener(this);
    }

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

    public void doneClick(){
        getNew();
        myIntent = getIntent();
        myBundle = new Bundle();
        myBundle.putSerializable("Edit Location", newLocation);
        myIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }

    public void getNew(){
        String city         = cityText.getText().toString();
        String country      = countryText.getText().toString();
        newCity             = new City(city, country);
        String description  = descriptionT.getText().toString();
        ArrayList<String> picArray  = new ArrayList<>();
        ArrayList<String> favPlaces = new ArrayList<>();
        favPlaces.add(favoritePlaces.getText().toString());
        newLocation = new Location(newCity, description, favPlaces, picArray);
    }

    public void dateClick(){

    }

    public void pictureClick(){

    }
}
