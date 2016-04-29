package com.minkim.traveldiary;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class WeatherAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Weather> days;
    public WeatherAdapter(Context context, ArrayList<Weather> days) {
        super(context, R.layout.weather, days);
        this.days = days;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView  = inflater.inflate(R.layout.weather, parent, false);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView main = (TextView) rowView.findViewById(R.id.main);
        TextView min  = (TextView) rowView.findViewById(R.id.min);
        TextView max  = (TextView) rowView.findViewById(R.id.max);
        TextView desc = (TextView) rowView.findViewById(R.id.desc);
        TextView hum  = (TextView) rowView.findViewById(R.id.hum);
        String dateS  = days.get(position).getDate();
        String mainS  = days.get(position).getMain();
        String minS   = days.get(position).getMin();
        String maxS   = days.get(position).getMax();
        String descS  = days.get(position).getDesc();
        String humS   = days.get(position).getHum();
        date.setText(dateS);
        main.setText("***" + mainS + "***");
        min.setText("The minimum temperature is: " + minS + " degrees.");
        max.setText("The maximum temperature is: " + maxS + " degrees.");
        desc.setText("The prediction for this day is: " + descS + ".");
        hum.setText("The humidity for this day is: " + humS + "%.");
        return rowView;
    }
}