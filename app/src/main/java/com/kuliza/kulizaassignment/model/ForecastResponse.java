package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * ForecastResponse Model
 */
public class ForecastResponse extends RealmObject {

    @PrimaryKey
    private String id = "first_entry";
    @SerializedName("location")
    private Location location;
    @SerializedName("current")
    private Current current;
    @SerializedName("forecast")
    private Forecast forecast;

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
