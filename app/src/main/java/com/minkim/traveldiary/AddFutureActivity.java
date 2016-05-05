package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class AddFutureActivity extends Activity implements View.OnClickListener {

    EditText description;
    Button pictures, done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_future);

        description = (EditText) findViewById(R.id.description);
        pictures    = (Button)   findViewById(R.id.pictures);
        done        = (Button)   findViewById(R.id.done);

        description.setOnClickListener(this);
        pictures.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.description:
                description();
                break;
            case R.id.pictures:
                pictures();
                break;
            case R.id.done:
                done();
                break;
            default:
                break;
        }
    }

    public void description() {

    }

    public void pictures() {

    }

    public void done() {

    }
}
