package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Forecast Model
 */
public class Forecast extends RealmObject {

    @SerializedName("forecastday")
    private RealmList<ForecastDay> forecastday;

    public RealmList<ForecastDay> getForecastday() {
        return forecastday;
    }
}
