package com.minkim.traveldiary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class TraveledActivity extends AppCompatActivity {

    ListView list;
    CheckBoxAdapter adapter;
    Button add, delete, edit, view;

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

    }
}
