package com.minkim.traveldiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.*;
import java.util.*;

public class FutureActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    ListView list;
    CheckBoxAdapter adapter;
    Button move, add, delete, edit, view, back;

    SQLiteDatabase sampleDB;
    String tableName_future  = "Future";
    String tableName_traveled = "Traveled";

    // for adding
    Location currentLocation;

    // For testing purposes
    ArrayList<Location> locations = new ArrayList<Location>();

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

        setOnClickListeners();

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

        try {
            sampleDB = openOrCreateDatabase("TravelDiary", MODE_PRIVATE, null);
            createTable();
        } catch (SQLiteException se){
            Log.e(getClass().getSimpleName(), "Couldn't create database");
        }

        adapter = new CheckBoxAdapter(this, locations);
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move:
                move();
                break;
            case R.id.add:
                add();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.edit:
                edit();
                break;
            case R.id.view:
                view();
                break;
        }
        for (Location i : locations)
            i.setSelected(false);
    }

    public void move() {
        Location temp = null;
        int count = 0;
        for (Location t: locations){
            if (t.isSelected()){
                count++;
                temp = t;
            }
        }
        if (count != 1)
            Toast.makeText(this, "Choose only ONE item to view", Toast.LENGTH_SHORT).show();
        else {
            ContentValues values = new ContentValues();
            values.put("City", temp.getCity().getCity());
            values.put("Country", temp.getCity().getCountry());
            values.put("Description", temp.getDescription());
            Log.i("Insert Data", temp.getCity().getCity());
            sampleDB.insert(tableName_traveled, null, values);
            locations.remove(temp);
            adapter.notifyDataSetChanged();
        }
    }

    public void add(){
        Intent intent = new Intent(FutureActivity.this, AddFutureActivity.class);
        startActivityForResult(intent, 100);
    }
    public void insertData(String cityValue, String countryValue) {
        ContentValues values = new ContentValues();
        values.put("City", cityValue);
        values.put("Country", countryValue);
        Log.i("Insert Data", cityValue);
        sampleDB.insert(tableName_future, null, values);
    }


    public void delete(){
        ArrayList<Location> rem = new ArrayList<Location>();
        for (Location t : locations) {
            if (t.isSelected())
                rem.add(t);
        }
        for (Location a : rem)
            locations.remove(a);
        adapter.notifyDataSetChanged();
    }

    public void edit(){}

    public void view(){}

    public void setOnClickListeners() {
        move.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        view.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void createTable() {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName_traveled +
                " (City VARCHAR, " +
                "  Country VARCHAR, " +
                "  Description VARCHAR);");
        Log.i("Created Table " + tableName_traveled, "Done");

        Log.d(getLocalClassName(), "in create table");
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName_future +
                " (City VARCHAR, " +
                "  Country VARCHAR);");
        Log.i("Created Table " + tableName_future, "Done");

    }

    public void onResume(){
        super.onResume();
        if (currentLocation != null) {
            locations.add(currentLocation);
            Log.i("location array", String.valueOf(locations.size()));
            adapter.notifyDataSetChanged();
            currentLocation = null;
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
            Log.i("after if", "here");
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }


}
