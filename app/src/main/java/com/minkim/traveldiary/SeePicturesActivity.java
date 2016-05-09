package com.minkim.traveldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.provider.*;
import android.database.*;
import android.net.*;
import android.util.*;

public class SeePicturesActivity extends AppCompatActivity {

    Button done;
    String picturePath1, picturePath2, picturePath3, picturePath4, picturePath5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_pictures);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        picturePath1 = myBundle.getString("picturePath1");
        picturePath2 = myBundle.getString("picturePath2");
        picturePath3 = myBundle.getString("picturePath3");
        picturePath4 = myBundle.getString("picturePath4");
        picturePath5 = myBundle.getString("picturePath5");

        done = (Button) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = getIntent();
                setResult(Activity.RESULT_OK, myIntent);
                finish();
            }
        });

        ImageView imageView1 = (ImageView) findViewById(R.id.imgView1);
        imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath1));
        ImageView imageView2 = (ImageView) findViewById(R.id.imgView2);
        imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath2));
        ImageView imageView3 = (ImageView) findViewById(R.id.imgView3);
        imageView3.setImageBitmap(BitmapFactory.decodeFile(picturePath3));
        ImageView imageView4 = (ImageView) findViewById(R.id.imgView4);
        imageView4.setImageBitmap(BitmapFactory.decodeFile(picturePath4));
        ImageView imageView5 = (ImageView) findViewById(R.id.imgView5);
        imageView5.setImageBitmap(BitmapFactory.decodeFile(picturePath5));
    }
}
