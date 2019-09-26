package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class RideListActivity extends AppCompatActivity {
    private ListView rideListView;
    private TextView total;
    static ArrayList<Ride> rideList = new ArrayList<Ride>();
    private RideArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rideListView = (ListView) findViewById(R.id.ride_list);
        total = (TextView) findViewById(R.id.text_total);

        setTotalRideDistanceText();

        adapter = new RideArrayAdapter(
                this,
                R.id.ride_summary,
                rideList
        );

        rideListView.setAdapter(adapter);

        rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride ride = (Ride) parent.getItemAtPosition(position);
                Intent i = new Intent(RideListActivity.this, ViewEditRideActivity.class);

                i.putExtra("position", position);
                i.putExtra("ride", ride);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(this, ViewEditRideActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//
//        if (b != null) {
//            if (b.containsKey("ride")) {
//                Ride ride = (Ride) i.getSerializableExtra("ride");
//
//                if (b.containsKey("position")) {
//                    // ride was edited
//                    int position = (int) i.getSerializableExtra("position");
//                    rideList.set(position, ride);
//                }
//                else {
//                    // ride was added
//                    rideList.add(ride);
//                }
//
//                setTotalRideDistanceText();
//                adapter.notifyDataSetChanged();
//            }
//
//            // clear data passed from view/edit activity to ensure ride is only added once
//            i.replaceExtras(new Bundle());
//        }
        adapter.notifyDataSetChanged();
    }

    private void setTotalRideDistanceText() {
        if (rideList.size() > 0) {
            // list contains at least one ride so sum the distance of each
            double totalDistance = 0;

            for (Ride r : rideList) {
                totalDistance += r.getDistance();
            }

            String text = totalDistance + " km total";

            total.setText(text);

            return;
        }

        // no rides in list yet
        total.setText(getString(R.string.list_empty));
    }
}
