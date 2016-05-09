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

/**
 * Created by roseanna on 5/4/16.
 */
public class EditTraveledActivity extends Activity  implements View.OnClickListener{
    EditText cityText, countryText, favoritePlaces, descriptionT;
    TextView dateText;
    Button done, dates, addPictures;
    Intent myIntent;
    Bundle myBundle;

    public Location newLocation;
    public City newCity;

    ArrayList<String> picArray  = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_traveled);

        cityText        = (EditText) findViewById(R.id.cityText);
        countryText     = (EditText) findViewById(R.id.countryText);
//        favoritePlaces  = (EditText) findViewById(R.id.favoritePlaces);
        descriptionT    = (EditText) findViewById(R.id.description);
        dateText        = (TextView) findViewById(R.id.dateText);
        done            = (Button) findViewById(R.id.done);
        dates           = (Button) findViewById(R.id.dates);
        addPictures     = (Button) findViewById(R.id.pictures);

        myIntent = getIntent();
        myBundle = myIntent.getExtras();
        Location oldLocation = (Location) myBundle.get("oldLocation");

        picArray = oldLocation.getPictures();

        cityText.setText(oldLocation.getCity().getCity());
        countryText.setText(oldLocation.getCity().getCountry());
//        favoritePlaces.setText(oldLocation.getFavoritePlaces().toString());
        descriptionT.setText(oldLocation.getDescription());
        dateText.setText(oldLocation.getDates());

        done.setOnClickListener(this);
        dates.setOnClickListener(this);
        addPictures.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done:
                doneClick();
                break;
            case R.id.dates:
                dateClick();
                break;
            case R.id.pictures:
                pictureClick();
                break;
            default:
                break;
        }
    }

    public void doneClick(){
        getNew();
        myIntent = getIntent();
        myBundle = new Bundle();
        Log.i("edit", newLocation.getCity().getCity());
        myBundle.putSerializable("Edit", newLocation);
        myIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }

    public void getNew(){
        String city         = cityText.getText().toString();
        String country      = countryText.getText().toString();
        String date         = dateText.getText().toString();

        newCity             = new City(city, country);
        String description  = descriptionT.getText().toString();
        ArrayList<String> favPlaces = new ArrayList<>();
//        favPlaces.add(favoritePlaces.getText().toString());
        newLocation = new Location(newCity, description, favPlaces, picArray, date);
    }

    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    String start;

    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }

    };
    private void updateLabel() {
        String myFormat         = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf    = new SimpleDateFormat(myFormat, Locale.US);
        start                   = sdf.format(myCalendar.getTime());
        Log.i("start", start);
        updateLabel2();
    }
    private void updateLabel2() {
        String myFormat         = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf    = new SimpleDateFormat(myFormat, Locale.US);
        String end              = sdf.format(myCalendar2.getTime());
        Log.i("end", end);
        dateText.setText(start + " - " + end);
    }
    public void dateClick(){
        new DatePickerDialog(EditTraveledActivity.this, endDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        new DatePickerDialog(EditTraveledActivity.this, startDate, myCalendar2
                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void pictureClick(){
        Intent intent = new Intent(EditTraveledActivity.this, AddPicturesActivity.class);
        Bundle myBundle = new Bundle();
        myBundle.putString("picturePath1", picArray.get(0));
        myBundle.putString("picturePath2", picArray.get(1));
        myBundle.putString("picturePath3", picArray.get(2));
        myBundle.putString("picturePath4", picArray.get(3));
        myBundle.putString("picturePath5", picArray.get(4));
        intent.putExtras(myBundle);
        startActivityForResult(intent, 100);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100){
                Bundle myBundle = data.getExtras();
                picArray.clear();
                picArray.add(myBundle.getString("picturePath1"));
                picArray.add(myBundle.getString("picturePath2"));
                picArray.add(myBundle.getString("picturePath3"));
                picArray.add(myBundle.getString("picturePath4"));
                picArray.add(myBundle.getString("picturePath5"));
            }
        }
        catch (Exception e){
            Log.i("ERROR..","onActivityResult");
        }
    }
}
