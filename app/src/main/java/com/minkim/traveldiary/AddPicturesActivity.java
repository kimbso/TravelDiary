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

public class AddPicturesActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE1 = 1;  /** Called when the activity is first created. */
    private static int RESULT_LOAD_IMAGE2 = 2;
    private static int RESULT_LOAD_IMAGE3 = 3;
    private static int RESULT_LOAD_IMAGE4 = 4;
    private static int RESULT_LOAD_IMAGE5 = 5;
    Button add1, add2, add3, add4, add5, del1, del2, del3, del4, del5, done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures);

        done = (Button) findViewById(R.id.done);
        add1 = (Button) findViewById(R.id.add1);
        add2 = (Button) findViewById(R.id.add2);
        add3 = (Button) findViewById(R.id.add3);
        add4 = (Button) findViewById(R.id.add4);
        add5 = (Button) findViewById(R.id.add5);
        del1 = (Button) findViewById(R.id.del1);
        del2 = (Button) findViewById(R.id.del2);
        del3 = (Button) findViewById(R.id.del3);
        del4 = (Button) findViewById(R.id.del4);
        del5 = (Button) findViewById(R.id.del5);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = getIntent();
                Bundle myBundle = new Bundle();
                myIntent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, myIntent);
                finish();
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE1);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE2);
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE3);
            }
        });

        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE4);
            }
        });

        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE5);
            }
        });

        del1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView1);
                imageView.setImageResource(0);
            }
        });

        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView2);
                imageView.setImageResource(0);
            }
        });

        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView3);
                imageView.setImageResource(0);
            }
        });

        del4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView4);
                imageView.setImageResource(0);
            }
        });

        del5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView5);
                imageView.setImageResource(0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView1);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
            else if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView2);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
            else if (requestCode == RESULT_LOAD_IMAGE3 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView3);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
            else if (requestCode == RESULT_LOAD_IMAGE4 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView4);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
            else if (requestCode == RESULT_LOAD_IMAGE5 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView5);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
        catch (Exception e){
            Log.i("ERROR", "SOME ISSUE");
        }
    }
}
