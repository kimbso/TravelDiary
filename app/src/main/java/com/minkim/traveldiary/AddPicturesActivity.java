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
    String picturePath1, picturePath2, picturePath3, picturePath4, picturePath5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        picturePath1 = myBundle.getString("picturePath1");
        picturePath2 = myBundle.getString("picturePath2");
        picturePath3 = myBundle.getString("picturePath3");
        picturePath4 = myBundle.getString("picturePath4");
        picturePath5 = myBundle.getString("picturePath5");

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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = getIntent();
                Bundle myBundle = new Bundle();
                myBundle.putString("picturePath1", picturePath1);
                myBundle.putString("picturePath2", picturePath2);
                myBundle.putString("picturePath3", picturePath3);
                myBundle.putString("picturePath4", picturePath4);
                myBundle.putString("picturePath5", picturePath5);
                myIntent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, myIntent);
                finish();
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("add", "1");
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
                picturePath1 = "";
            }
        });

        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView2);
                imageView.setImageResource(0);
                picturePath2 = "";
            }
        });

        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView3);
                imageView.setImageResource(0);
                picturePath3 = "";
            }
        });

        del4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView4);
                imageView.setImageResource(0);
                picturePath4 = "";
            }
        });

        del5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imgView5);
                imageView.setImageResource(0);
                picturePath5 = "";
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
                picturePath1 = picturePath;
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
                picturePath2 = picturePath;
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
                picturePath3 = picturePath;
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
                picturePath4 = picturePath;
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
                picturePath5 = picturePath;
            }
        }
        catch (Exception e){
            Log.i("ERROR", "SOME ISSUE");
        }
    }
}
