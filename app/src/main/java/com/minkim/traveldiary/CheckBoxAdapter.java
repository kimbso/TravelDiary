package com.minkim.traveldiary;
// see assignment 2a

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

        cb.setText(locations.get(position).getCity().getCity());

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    cb.setChecked(false);
                }
                else {
                    cb.setChecked(true);
                }
            }
        });
        return rowView;
    }

    @Override
    public int getCount(){
        return locations.size();
    }
}
