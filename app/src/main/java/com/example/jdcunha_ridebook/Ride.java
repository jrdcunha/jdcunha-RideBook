package com.example.jdcunha_ridebook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ride implements Serializable {
    private Date date;
    private double distance;
    private double averageSpeed;
    private int averageCadence;
    private String comment;

    public Ride(Date date, double distance, double averageSpeed, int averageCadence, String comment) {
        this.date = date;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.averageCadence = averageCadence;
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDistance(double distance) {
        if (distance > 0) {
            this.distance = distance;
        }
    }

    public double getDistance() {
        return this.distance;
    }

    public void setAverageSpeed(double averageSpeed) {
        if (averageSpeed > 0) {
            this.averageSpeed = averageSpeed;
        }
    }

    public double getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageCadence(int averageCadence) {
        if (averageCadence > 0) {
            this.averageCadence = averageCadence;
        }
    }

    public int getAverageCadence() {
        return this.averageCadence;
    }

    public void setComment(String comment) {
        if (comment.length() <= 20) {
            this.comment = comment;
        }
    }

    public String getComment() {
        return this.comment;
    }

    public String getRideSummary() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd \u2022 h:mm aa");
        return formatter.format(getDate()) + " \u2022 " + getDistance() + " km";
    }
}
