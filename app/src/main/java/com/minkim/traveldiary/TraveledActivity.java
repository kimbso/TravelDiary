package com.minkim.traveldiary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class TraveledActivity extends Activity implements View.OnClickListener {

    ListView list;
    CheckBoxAdapter adapter;
    Button add, delete, edit, view, back;
    ArrayList<Location> locationArrayList;
    Location currentLocation, editLocation;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveled);

        final Intent intent = getIntent();

        list    = (ListView) findViewById(R.id.list);
        add     = (Button) findViewById(R.id.add);
        delete  = (Button) findViewById(R.id.delete);
        edit    = (Button) findViewById(R.id.edit);
        view    = (Button) findViewById(R.id.view);
        back    = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                myBundle.putString("task", "back");
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        setClicks();

        currentLocation = null;
        locationArrayList = new ArrayList<>();

        adapter = new CheckBoxAdapter(this, locationArrayList);
        list.setAdapter(adapter);
    }

    public void setClicks(){
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                addClick();
                break;
            case R.id.delete:
                deleteClick();
                break;
            case R.id.edit:
                editClick();
                break;
            case R.id.view:
                viewClick();
                break;
            default:
                Log.i("ONCLICK", "DEFAULT");
                break;
        }
    }

    public void addClick(){
        Intent intent = new Intent(TraveledActivity.this, AddTraveledActivity.class);
        startActivityForResult(intent, 100);
    }

    public void deleteClick(){
        int count = 0;
        for (Location t: locationArrayList){
            if (t.isSelected()){
                count++;
                locationArrayList.remove(t);
            }
        }
        if (count == 0)
            Toast.makeText(this, "Choose something to delete", Toast.LENGTH_SHORT).show();
        else
            adapter.notifyDataSetChanged();
    }

    public void editClick(){
        Location temp = null;
        int count = 0, index = 0;
        for (Location t: locationArrayList){
            if (t.isSelected()){
                temp = t;
                count++;
                selectedIndex = index;
            }
            index++;
        }

        if (count != 1 && temp != null)
            Toast.makeText(this, "Choose only ONE item to edit", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(TraveledActivity.this, EditTraveledActivity.class);
            Bundle myBundle = new Bundle();
            myBundle.putSerializable("oldLocation", temp);
            intent.putExtras(myBundle);
            startActivityForResult(intent, 200);
        }
    }

    public void viewClick(){
        int count = 0;
        for (Location t: locationArrayList){
            if (t.isSelected()){
                count++;
            }
        }
        if (count != 1)
            Toast.makeText(this, "Choose only ONE item to view", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Roseanna: Implement View", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TraveledActivity.this, AddTraveledActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    public void onResume(){
        super.onResume();
        if (currentLocation != null) {
            locationArrayList.add(currentLocation);
            Log.i("location array", String.valueOf(locationArrayList.size()));
            adapter.notifyDataSetChanged();
            currentLocation = null;
        }
        if (selectedIndex != -1 && editLocation != null){
            locationArrayList.remove(selectedIndex);
            locationArrayList.add(selectedIndex, editLocation);
            selectedIndex = -1;
            adapter.notifyDataSetChanged();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Adding location
            if (requestCode == 100){
                Bundle myBundle = data.getExtras();

                Location newLocation    = (Location) myBundle.get("Location");
                currentLocation         = newLocation;

                String description      = newLocation.getDescription();
                String cityName         = newLocation.getCity().getCity();
                String countryN         = newLocation.getCity().getCountry();

                Log.i("City from add", cityName);
                Log.i("Country from add", countryN);
                Log.i("Description from add", description);
            }
            if (resultCode != 100){
                Log.i("200 request Code", String.valueOf(requestCode));
                Log.i("in request200", "here");
                Bundle myBundle = data.getExtras();
                editLocation = (Location) myBundle.get("Edit");
                Log.i("edit location", editLocation.toString());
                String description      = editLocation.getDescription();
                String cityName         = editLocation.getCity().getCity();
                String countryN         = editLocation.getCity().getCountry();

                Log.i("City from edit", cityName);
                Log.i("Country from edit", countryN);
                Log.i("Description from edit", description);

            }
            Log.i("after if", "here");
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }

}
