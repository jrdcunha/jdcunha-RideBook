package com.example.jdcunha_ridebook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A custom array adapter for displaying Ride objects in a ListView.
 */
public class RideArrayAdapter extends ArrayAdapter<Ride> {
    private Activity activity;
    private ArrayList<Ride> rideList;
    private static LayoutInflater inflater = null;

    /**
     * Initializes a new instance of the RideArrayAdapter class.
     */
    public RideArrayAdapter(Activity activity, int textViewResourceId, ArrayList<Ride> rideList) {
        super(activity, textViewResourceId, rideList);

        this.activity = activity;
        this.rideList = rideList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the number of rides in the list.
     */
    public int getCount() {
        return rideList.size();
    }

    /**
     * Maintains the view that will contain a ride's summary info.
     */
    public static class ViewHolder {
        public TextView rideSummary;
    }

    /**
     * Handles creation of the list item view.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        // check if view is new or reused to determine whether it should be inflated
        if (convertView == null) {
            // view is used
            view = inflater.inflate(R.layout.activity_listview, null);
            holder = new ViewHolder();
            holder.rideSummary = (TextView) view.findViewById(R.id.ride_summary);
            view.setTag(holder);
        }
        else {
            // view is reused
            holder = (ViewHolder) view.getTag();
        }

        // populate the list item view with the ride's summary info
        holder.rideSummary.setText(rideList.get(position).getRideSummary());

        return view;
    }
}
