package com.bridgelabz.app.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bridgelabz.app.R;

/**
 * Created by bridgelabz5 on 26/5/16.
 */
public class Widgets extends Fragment {

    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.widget,container,false);

        Button dateButton,timeButton;
        dateButton=(Button)view.findViewById(R.id.dateButton);
        timeButton=(Button)view.findViewById(R.id.timeButton);
        final TextView date=(TextView)view.findViewById(R.id.date);
        final TextView time=(TextView)view.findViewById(R.id.time);
        //Set On click listener for DateButton Click
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Date picker dialog.
                new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                    //When  date is set.
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(""+dayOfMonth+" / "+monthOfYear+" / "+year);
                    }
                }, 2015, 10, 10).show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Time picker dialog.
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(""+hourOfDay+" : "+minute);
                    }
                }, 11, 56, true).show();


            }
        });
        return view;
    }
}
