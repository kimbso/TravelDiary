package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ViewFutureActivity extends Activity implements View.OnClickListener {

    TextView locationText, description;
    Button done;

    Intent myIntent;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_future);

        locationText = (TextView) findViewById(R.id.locationText);
        description = (TextView) findViewById(R.id.description);
        done = (Button) findViewById(R.id.done);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        Location currentLocation = (Location) myBundle.get("ViewLocation");

        if(currentLocation != null) {
            Log.i("CURRENT LOCATION", currentLocation.getLocation());

            String city = currentLocation.getLocation();
            locationText.setText(city);
            if(currentLocation.getDescription().equals(""))
                description.setText("No current description. Please edit to add new description");
            else
                description.setText(currentLocation.getDescription());

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(Activity.RESULT_OK, myIntent);
                    finish();
                }
            });
        }
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
