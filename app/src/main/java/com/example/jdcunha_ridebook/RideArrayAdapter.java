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

public class RideArrayAdapter extends ArrayAdapter<Ride> {
    private Activity activity;
    private ArrayList<Ride> rideList;
    private static LayoutInflater inflater = null;

    public RideArrayAdapter(Activity activity, int textViewResourceId, ArrayList<Ride> rideList) {
        super(activity, textViewResourceId, rideList);

        this.activity = activity;
        this.rideList = rideList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return rideList.size();
    }

    public Ride getRide(Ride position) {
        return position;
    }

    public int getRideId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView rideSummary;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.activity_listview, null);
            holder = new ViewHolder();
            holder.rideSummary = (TextView) view.findViewById(R.id.ride_summary);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.rideSummary.setText(rideList.get(position).getRideSummary());

        return view;
    }
}
