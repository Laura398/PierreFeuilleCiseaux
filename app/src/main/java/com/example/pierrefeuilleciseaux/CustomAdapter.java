package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pierrefeuilleciseaux.R;
import com.example.pierrefeuilleciseaux.TextSwitch;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<TextSwitch> {

    public CustomAdapter(Context context, ArrayList<TextSwitch> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextSwitch ts = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.text_field, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.textView);
        Switch sw = convertView.findViewById(R.id.switch1);

        textView.setText(ts.getText());
        sw.setChecked(ts.isSelected());
        sw.setOnClickListener(view -> {
            ts.setSelected(sw.isChecked());
        });
        return convertView;
    }
}