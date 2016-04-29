package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.*;
import java.util.*;

public class FutureActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

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

        setOnClickListeners();

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
        //for (Task i : tasks)
        //    i.setSelected(false);
    }

    public void move() {
    }

    public void add() {
    }

    public void delete(){
        ArrayList<Location> rem = new ArrayList<Location>();
        for (Location t : locations) {
            if (t.isSelected())
                rem.add(t);
        }
        for (Location a : rem)
            tasks.remove(a);
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
}
