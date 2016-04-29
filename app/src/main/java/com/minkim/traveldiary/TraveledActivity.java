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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

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
    Button add, delete, edit, view;
    ArrayList<Location> locationArrayList;
    Location newLocation;

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

    }

    public void editClick(){

    }

    public void viewClick(){

    }

    public void onResume(){
        super.onResume();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Adding location
            if (requestCode == 100){
                Bundle myBundle = data.getExtras();
                newLocation = (Location) myBundle.get("Location");
                Log.i("City from add", newLocation.getCity().getCity());
            }
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }

}
