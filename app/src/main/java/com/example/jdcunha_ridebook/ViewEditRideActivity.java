package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Allows details of a ride to be defined and maintained.
 */
public class ViewEditRideActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView date;
    private TextView time;

    private EditText distance;
    private EditText averageSpeed;
    private EditText averageCadence;
    private EditText comment;

    private Button save;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    private Ride ride;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_ride);

        // format for date and time
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        timeFormatter = new SimpleDateFormat("HH:mm");

        // set view references
        findViewsById();

        // initialize date and time pickers
        preparePickers();

        // set on-click listeners for pickers and save button
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        save.setOnClickListener(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        // set references if Ride object and its position in the list were passed into this activity
        if (b != null) {
            if (b.containsKey("ride")) {
                ride = (Ride) i.getSerializableExtra("ride");
            }
            if (b.containsKey("position")) {
                position = (int) i.getSerializableExtra("position");
            }
        }

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

    /**
     * Set references to each view in the activity.
     */
    private void findViewsById() {
        date = (TextView) findViewById(R.id.text_date);
        time = (TextView) findViewById(R.id.text_time);
        distance = (EditText) findViewById(R.id.text_distance);
        averageSpeed = (EditText) findViewById(R.id.text_average_speed);
        averageCadence = (EditText) findViewById(R.id.text_average_cadence);
        comment = (EditText) findViewById(R.id.text_comment);
        save = (Button) findViewById(R.id.button_save);
    }

    /**
     * Set up the date picker and time picker to be used to set the date and time fields.
     */
    private void preparePickers() {
        Calendar newCalendar = Calendar.getInstance();

        // initialize date picker dialog fragment
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        // on confirm, date field is populated with chosen date
                        date.setText(dateFormatter.format(newDate.getTime()));
                        // date is mandatory and has been entered, so remove error state if any
                        date.setError(null);
                    }

                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        // initialize time picker dialog fragment
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // on confirm, time field is populated with chosen date
                        time.setText(String.format("%02d:%02d", hourOfDay, minute));
                        // time is mandatory and has been entered, so remove error state if any
                        time.setError(null);
                    }
                },
                newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE),
                true);
    }

    /**
     * Populates each field with data from the Ride object passed into this activity.
     */
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
        else if (view == save) {
            // on save button click, ensure all data is valid before allowing a save
            if (validate()) {
                save();
            }
        }
    }

    /**
     * Determines if all fields contain valid data.
     */
    private boolean validate() {
        boolean isValid = true;

        // date, time, distance, average speed, and average cadence are mandatory - if at least
        // one of them is empty, validation fails

        if (date.getText().toString().trim().isEmpty()) {
            date.setError(getString(R.string.error_required_field));
            isValid = false;
        }

        if (time.getText().toString().trim().isEmpty()) {
            time.setError(getString(R.string.error_required_field));
            isValid = false;
        }

        if (distance.getText().toString().trim().isEmpty()) {
            distance.setError(getString(R.string.error_required_field));
            isValid = false;
        }

        if (averageSpeed.getText().toString().trim().isEmpty()) {
            averageSpeed.setError(getString(R.string.error_required_field));
            isValid = false;
        }

        if (averageCadence.getText().toString().trim().isEmpty()) {
            averageCadence.setError(getString(R.string.error_required_field));
            isValid = false;
        }

        return isValid;
    }

    /**
     * Creates a new Ride object or updates an existing Ride object and passes it back to the
     * list activity.
     */
    private void save() {
        Intent i = new Intent(ViewEditRideActivity.this, RideListActivity.class);

        if (ride != null) {
            // in "View/Edit" mode, so overwrite existing ride's data
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date newDateTime = dateTimeFormatter.parse(String.format(
                        "%s %s",
                        date.getText().toString(),
                        time.getText().toString()
                ));

                ride.setDate(newDateTime);
            }
            catch(ParseException pe) {
                // to make the IDE stop complaining about possible unparseable date string
            }

            ride.setDistance(Double.parseDouble(distance.getText().toString()));
            ride.setAverageSpeed(Double.parseDouble(averageSpeed.getText().toString()));
            ride.setAverageCadence(Integer.parseInt(averageCadence.getText().toString()));
            ride.setComment(comment.getText().toString());

            // send position back to list activity so it knows which Ride object in the list to
            // update
            i.putExtra("position", position);
        }
        else {
            // in "Add" mode, so create new ride
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date newDateTime = dateTimeFormatter.parse(String.format(
                        "%s %s",
                        date.getText().toString(),
                        time.getText().toString()
                ));

                ride = new Ride(
                        newDateTime,
                        Double.parseDouble(distance.getText().toString()),
                        Double.parseDouble(averageSpeed.getText().toString()),
                        Integer.parseInt(averageCadence.getText().toString()),
                        comment.getText().toString()
                );
            }
            catch(ParseException pe) {
                // to make the IDE stop complaining about possible unparseable date string
            }
        }

        i.putExtra("ride", ride);
        // prevent user from using device back button to return to this activity
        finish();
        startActivity(i);
    }
}
