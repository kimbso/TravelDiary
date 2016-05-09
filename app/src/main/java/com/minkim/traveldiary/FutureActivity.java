package com.minkim.traveldiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.concurrent.Future;

public class FutureActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    ListView list;
    CheckBoxAdapter adapter;
    Button move, add, delete, edit, view, back;
    Cursor cursor;

    SQLiteDatabase sampleDB;
    String tableName_future  = "Future";
    String tableName_traveled = "Traveled";
    String filename = "future";

    Location editLocation;
    int selectedIndex = -1;

    // for adding
    Location currentLocation;

    // For testing purposes
    ArrayList<Location> locations = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        list    = (ListView) findViewById(R.id.list);
        move    = (Button) findViewById(R.id.move);
        add     = (Button) findViewById(R.id.add);
        delete  = (Button) findViewById(R.id.delete);
        edit    = (Button) findViewById(R.id.edit);
        view    = (Button) findViewById(R.id.view);
        back    = (Button) findViewById(R.id.back);

        setClicks();

        try {
            sampleDB = openOrCreateDatabase("TravelDiary", MODE_PRIVATE, null);
            createTable();
        } catch (SQLiteException se){
            Log.e(getClass().getSimpleName(), "Couldn't create database");
        }

        adapter = new CheckBoxAdapter(this, locations);
        list.setAdapter(adapter);
    }

    public void onStart() {
        super.onStart();
        Object o = load(this, filename);

        if (o != null && o instanceof ArrayList)
            locations = (ArrayList<Location>) o;
        else
            locations = new ArrayList<Location>();

        adapter = new CheckBoxAdapter(this, locations);
        list.setAdapter(adapter);

        setClicks();
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
            case R.id.back:
                back();
                break;
            case R.id.view:
                view();
                break;
        }
        for (Location i : locations)
            i.setSelected(false);
    }

    public void back(){
        final Intent intent = getIntent();
        Bundle myBundle = new Bundle();
        myBundle.putString("task", "back");
        intent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
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

    public void edit(){
        Location temp = null;
        int count = 0, index = 0;
        for (Location t: locations){
            if (t.isSelected()){
                temp = t;
                count++;
                selectedIndex = index;
            }
            index++;
        }
        Log.i("count", String.valueOf(count));
        if (count != 1 && temp != null)
            Toast.makeText(this, "Choose only ONE item to edit", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(FutureActivity.this, EditFutureActivity.class);
            Bundle myBundle = new Bundle();
            myBundle.putSerializable("oldLocation", temp);
            intent.putExtras(myBundle);
            startActivityForResult(intent, 200);
        }
    }

    public void view(){
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
            Log.i("VIEW CLICK", "MAKING BUNDLE");
            Bundle myBundle = new Bundle();
            myBundle.putSerializable("ViewLocation", temp);
            Intent myIntent = new Intent(FutureActivity.this, ViewFutureActivity.class);
            myIntent.putExtras(myBundle);
            startActivityForResult(myIntent, 300);
        }
    }

    public void setClicks() {
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
                " (City VARCHAR," +
                "  Country VARCHAR," +
                "  Description VARCHAR);");
        Log.i("Created Table " + tableName_future, "Done");

    }

    public void onResume(){
        super.onResume();
        updateList();
        if (currentLocation != null) {
            locations.add(currentLocation);
            Log.i("location array", String.valueOf(locations.size()));
            adapter.notifyDataSetChanged();
            currentLocation = null;
        }
        if (selectedIndex != -1 && editLocation != null){
            locations.remove(selectedIndex);
            locations.add(selectedIndex, editLocation);
            selectedIndex = -1;
            editLocation = null;
            adapter.notifyDataSetChanged();
        }
    }


    public void updateList(){
        Log.i("update", "list");
        cursor = sampleDB.rawQuery("SELECT City, Country, Description FROM " + tableName_future, null);
        if(cursor != null) {
            Log.i("cursor Column", String.valueOf(cursor.getColumnCount()));
            Log.i("cursor", String.valueOf(cursor.getCount()));
            for(int i = 0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                String cityVal          = cursor.getString(cursor.getColumnIndex("City"));
                String countryVal       = cursor.getString(cursor.getColumnIndex("Country"));
                String description      = cursor.getString(cursor.getColumnIndex("Description"));
                Log.i(cityVal, description);
                City newCity            = new City(cityVal, countryVal);
                Log.i("city val", countryVal);
                Location newLocation    = new Location(newCity, description, null, null);
                locations.add(newLocation);
            }
            Log.i("cursor", "not null");
            cursor.close();
            sampleDB.execSQL("Delete from " + tableName_future);
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
            // Edit
            else{
                Log.i("200 request Code", String.valueOf(requestCode));
                Log.i("in request200", "here");
                Bundle myBundle = data.getExtras();
                editLocation = (Location) myBundle.get("Edit");
                Log.i("edit location", editLocation.toString());
                String description      = editLocation.getDescription();
                String cityName         = editLocation.getCity().getCity();
                String countryN         = editLocation.getCity().getCountry();
                String dates            = editLocation.getDates();

                Log.i("City from edit", cityName);
                Log.i("Country from edit", countryN);
                Log.i("Description from edit", description);
                Log.i("Dates from edit", dates);
            }
            Log.i("after if", "here");
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }

    public void onStop() {
        super.onStop();
        save(this, filename, locations);
    }
    public static void save(Context context, String fileName, Object obj) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);
            oos.close();
        } catch (Exception e) {
            Log.e("A", "EXCEPTION: " + e.getMessage());
        }
    }
    public static Object load(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            Log.e("B", "EXCEPTION: " + e.getMessage());
            return null;
        }
    }


}
