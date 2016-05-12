package com.minkim.traveldiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditFutureActivity extends Activity implements View.OnClickListener {

    EditText cityText, description;
    Button done;

    Intent myIntent;
    Bundle myBundle;

    public Location newLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_future);

        cityText = (EditText) findViewById(R.id.cityText);
        description = (EditText) findViewById(R.id.description);
        done = (Button) findViewById(R.id.done);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        Location oldLocation = (Location) myBundle.get("oldLocation");

        cityText.setText(oldLocation.getLocation());
        description.setText(oldLocation.getDescription());

        done.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done:
                doneClick();
                break;
            default:
                break;
        }
    }

    public void doneClick(){
        getNew();
        myIntent = getIntent();
        myBundle = new Bundle();
        Log.i("edit", newLocation.getLocation());
        myBundle.putSerializable("Edit", newLocation);
        myIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }

    public void getNew(){
        String city         = cityText.getText().toString();

        String desc  = description.getText().toString();
        ArrayList<String> picArray  = new ArrayList<>();
        ArrayList<String> favPlaces = new ArrayList<>();
        newLocation = new Location(city, desc, picArray);
    }
}
