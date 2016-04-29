package com.minkim.traveldiary;

import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.util.*;
import android.content.*;
import android.app.Activity;

public class MainActivity extends Activity {

    Button traveled, future, discover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        traveled = (Button) findViewById(R.id.traveled);
        future   = (Button) findViewById(R.id.future);
        discover = (Button) findViewById(R.id.discover);

        traveled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TraveledActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FutureActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiscoverActivity.class);
                startActivityForResult(intent, 300);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100){
            }
            else if (requestCode == 200){
                Bundle futureData = data.getExtras();
                String task = futureData.getString("task");
                if (task.equals("move")){

                }
            }
            else if (requestCode == 300){
            }
        }
        catch (Exception e){
            Log.i("ERROR","onActivityResult");
        }
    }
}
