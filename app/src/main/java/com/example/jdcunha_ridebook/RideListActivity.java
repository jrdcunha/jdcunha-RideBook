package com.example.jdcunha_ridebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
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

/**
 * Shows a list of rides and allows for adding new rides as well as editing and deleting
 * existing ones.
 */
public class RideListActivity extends AppCompatActivity {
    private ListView rideListView;
    private TextView total; // the text below the list informing the user of their total ride distance
    private static ArrayList<Ride> rideList = new ArrayList<Ride>();
    private RideArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rideListView = (ListView) findViewById(R.id.ride_list);
        registerForContextMenu(rideListView); // on long-click menu for deleting a ride

        total = (TextView) findViewById(R.id.text_total);

        updateTotalRideDistanceText();

        // bind list to adapter

        adapter = new RideArrayAdapter(
                this,
                R.id.ride_summary,
                rideList
        );

        rideListView.setAdapter(adapter);

        // clicking on a ride item allows it to be edited
        rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride ride = (Ride) parent.getItemAtPosition(position);
                Intent i = new Intent(RideListActivity.this, ViewEditRideActivity.class);

                // send the ride to the view/edit activity and keep track of its position so it can
                // be updated in the list later
                i.putExtra("position", position);
                i.putExtra("ride", ride);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // options menu contains button for adding a ride
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        // context menu contains button for deleting a ride
        super.onCreateContextMenu(menu, view, info);

        if (view.getId() == R.id.ride_list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete:
                // remove ride from list and update the total distance text
                rideList.remove(info.position);
                adapter.notifyDataSetChanged();
                updateTotalRideDistanceText();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                // go to view/edit activity
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

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            // came from view/edit activity
            if (b.containsKey("ride")) {
                Ride ride = (Ride) i.getSerializableExtra("ride");

                if (b.containsKey("position")) {
                    // ride was edited
                    int position = (int) i.getSerializableExtra("position");
                    rideList.set(position, ride);
                }
                else {
                    // ride was added
                    rideList.add(ride);
                }

                updateTotalRideDistanceText();
                adapter.notifyDataSetChanged();
            }

            // clear data passed from view/edit activity to ensure ride is only added once
            i.replaceExtras(new Bundle());
        }
    }

    /**
     * Sets the text below the list of rides to indicate the current total distance travelled.
     */
    private void updateTotalRideDistanceText() {
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
