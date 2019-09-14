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

        rideListView = (ListView) findViewById(R.id.ride_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter<Ride>(
                this,
                R.layout.activity_listview,
                R.id.text_view,
                rideList
        );
        rideListView.setAdapter(arrayAdapter);
    }
}
