package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class RideListActivity extends AppCompatActivity {
    // list of rides
    ListView rideListView;
    ArrayList rideList = new ArrayList<Ride>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rideList.add(new Ride(
                new Date(),
                1.0,
                1.0,
                1,
                "test"
        ));
        rideList.add(new Ride(
                new Date(),
                2.0,
                2.0,
                2,
                "test2"
        ));

        rideListView = (ListView) findViewById(R.id.ride_list);
        RideArrayAdapter adapter = new RideArrayAdapter(
                this,
                R.id.ride_summary,
                rideList
        );
        rideListView.setAdapter(adapter);
    }
}
