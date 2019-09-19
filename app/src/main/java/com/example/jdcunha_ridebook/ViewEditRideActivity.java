package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewEditRideActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText date;

    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat formatter;

    private Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_ride);

        formatter = new SimpleDateFormat("yyyy-MM-dd");

        date = (EditText) findViewById(R.id.text_date);

        date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        date.setText(formatter.format(newDate.getTime()));
                    }

                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        Intent i = getIntent();
        ride = (Ride) i.getSerializableExtra("ride");

        // if existing ride was clicked, populate fields with its data to be viewed/edited,
        // otherwise fields will be empty to define a new ride to be added to the list
        if (ride != null) {
            populateFields();
        }
    }

    private void populateFields() {
        date.setText(formatter.format(ride.getDate()));
    }

    @Override
    public void onClick(View view) {
        if (view == date) {
            datePickerDialog.show();
        }
    }
}
