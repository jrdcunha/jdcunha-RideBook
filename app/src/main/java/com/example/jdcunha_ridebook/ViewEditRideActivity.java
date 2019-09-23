package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewEditRideActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText date;
    private EditText time;
    private EditText distance;
    private EditText averageSpeed;
    private EditText averageCadence;
    private EditText comment;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    private Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_ride);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        timeFormatter = new SimpleDateFormat("h:mm aa");

        findViewsById();
        preparePickers();

        date.setOnClickListener(this);
        time.setOnClickListener(this);

        Intent i = getIntent();
        ride = (Ride) i.getSerializableExtra("ride");

        // if existing ride was clicked, populate fields with its data to be viewed/edited,
        // otherwise fields will be empty to define a new ride to be added to the list
        if (ride != null) {
            // "View/Edit" mode
            setTitle(getString(R.string.title_edit_ride));
            populateFields();
        }
        else {
            // "Add" mode
            setTitle(getString(R.string.title_add_ride));
        }
    }

    private void findViewsById() {
        date = (EditText) findViewById(R.id.text_date);
        time = (EditText) findViewById(R.id.text_time);
        distance = (EditText) findViewById(R.id.text_distance);
        averageSpeed = (EditText) findViewById(R.id.text_average_speed);
        averageCadence = (EditText) findViewById(R.id.text_average_cadence);
        comment = (EditText) findViewById(R.id.text_comment);
    }

    private void preparePickers() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        date.setText(dateFormatter.format(newDate.getTime()));
                    }

                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm = hourOfDay < 12 ? "AM" : "PM";
                        int displayHour = hourOfDay < 12 ? hourOfDay : hourOfDay - 12;
                        time.setText(String.format("%d:%02d %s", displayHour, minute, am_pm));
                    }
                },
                newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE),
                false);
    }

    private void populateFields() {
        Date rideDateTime = ride.getDate();
        date.setText(dateFormatter.format(rideDateTime));
        time.setText(timeFormatter.format(rideDateTime));
        distance.setText(String.valueOf(ride.getDistance()));
        averageSpeed.setText(String.valueOf(ride.getAverageSpeed()));
        averageCadence.setText(String.valueOf(ride.getAverageCadence()));
        comment.setText(ride.getComment());
    }

    @Override
    public void onClick(View view) {
        if (view == date) {
            datePickerDialog.show();
        }
        else if (view == time) {
            timePickerDialog.show();
        }
    }
}
