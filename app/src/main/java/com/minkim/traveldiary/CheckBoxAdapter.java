package com.minkim.traveldiary;
// see assignment 3a for reference

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.*;

public class CheckBoxAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Location> locations;

    public CheckBoxAdapter(Context context, ArrayList<Location> locations) {
        super(context, R.layout.checkboxrow, locations);
        this.context = context;
        this.locations = locations;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.checkboxrow, parent, false);
        final CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkbox);

        cb.setText(locations.get(position).getCity().getCity() + ", " +
                locations.get(position).getCity().getCountry());

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked())
                    locations.get(position).setSelected(true);
                else if (!cb.isChecked())
                    locations.get(position).setSelected(false);
            }
        });
        return rowView;
    }

    @Override
    public int getCount(){
        return locations.size();
    }
}
