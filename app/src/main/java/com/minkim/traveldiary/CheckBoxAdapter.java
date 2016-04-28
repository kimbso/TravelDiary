package com.minkim.traveldiary;
// see assignment 2a

import android.content.Context;
import android.view.*;
import android.widget.*;

public class CheckBoxAdapter extends ArrayAdapter {

    private final Context context;
    private final String[] values;

    public CheckBoxAdapter(Context context, String[] values, int[] pics) {
        super(context, R.layout.checkboxrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.checkboxrow, parent, false);
        CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkbox);

        cb.setText(values[position]);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return rowView;
    }

    @Override
    public int getCount(){
        return values.length;
    }
}
