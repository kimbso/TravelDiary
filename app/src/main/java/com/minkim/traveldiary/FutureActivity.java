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

import java.io.*;
import java.util.*;

public class FutureActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    ListView list;
    CheckBoxAdapter adapter;
    Button move, add, delete, edit, view, back;

    SQLiteDatabase sampleDB;
    String tableName  = "Future";
    String tableName2 = "Traveled";


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

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                myBundle.putString("task", "move");
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
    }

    public void insertData(String city, String country, String description, String fav, String date) {
        ContentValues values = new ContentValues();
        values.put("City", city);
        values.put("Country", country);
        values.put("Description", description);
        values.put("FavoritePlaces", fav);
        values.put("Date", date);
        Log.i("Insert Data", city);
        sampleDB.insert(tableName, null, values);
        updateList();
    }


    private void deleteData(String date, String city) {
        sampleDB.delete(tableName, "Date=?", new String[]{date}, "City=?", new String[]{city});
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
        Log.d(getLocalClassName(), "in create table");
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                " (City VARCHAR, " +
                "  Country VARCHAR, " +
                "  Description VARCHAR" +
                "  FavoritePlaces VARCHAR" +
                "  Date VARCHAR);");
        Log.i("Created Table", "Done");

        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName2 +
                " (City VARCHAR, " +
                "  Country VARCHAR, " +
                "  Description VARCHAR" +
                "  FavoritePlaces VARCHAR" +
                "  Date VARCHAR);");
        Log.i("Created Table", "Done");
    }


}
