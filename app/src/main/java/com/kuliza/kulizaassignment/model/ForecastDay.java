package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * ForecastDay Model
 */
public class ForecastDay extends RealmObject {

    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }
}
