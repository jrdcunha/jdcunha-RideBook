package com.example.jdcunha_ridebook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Maintains details of a bicycle ride.
 */
public class Ride implements Serializable {
    private Date date;              // the date and time of the start of the ride
    private double distance;        // the distance travelled during the ride (in km)
    private double averageSpeed;    // the average speed attained during the ride (in km/h)
    private int averageCadence;     // the average cadence attained during the ride (in rpm)
    private String comment;         // short additional info about the ride

    /**
     * Initializes a new instance of the Ride class.
     */
    public Ride(Date date, double distance, double averageSpeed, int averageCadence, String comment) {
        this.date = date;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.averageCadence = averageCadence;
        this.comment = comment;
    }

    /**
     * Sets the date according to the specified Date object.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Sets the distance according to the specified value.
     */
    public void setDistance(double distance) {
        if (distance > 0) {
            this.distance = distance;
        }
    }

    /**
     * Returns the distance.
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Sets the average speed according to the specified value.
     */
    public void setAverageSpeed(double averageSpeed) {
        if (averageSpeed > 0) {
            this.averageSpeed = averageSpeed;
        }
    }

    /**
     * Returns the average speed.
     */
    public double getAverageSpeed() {
        return this.averageSpeed;
    }

    /**
     * Sets the average cadence according to the specified value.
     */
    public void setAverageCadence(int averageCadence) {
        if (averageCadence > 0) {
            this.averageCadence = averageCadence;
        }
    }

    /**
     * Returns the average cadence.
     */
    public int getAverageCadence() {
        return this.averageCadence;
    }

    /**
     * Sets the comment according to the specified string.
     */
    public void setComment(String comment) {
        if (comment.length() <= 20) {
            this.comment = comment;
        }
    }

    /**
     * Returns the comment.
     */
    public String getComment() {
        return this.comment;
    }

    /** Returns a formatted string containing the ride's date, time, and distance
     * for displaying in the ListView item.
     */
    public String getRideSummary() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd \u2022 HH:mm");
        return formatter.format(getDate()) + " \u2022 " + getDistance() + " km";
    }
}
