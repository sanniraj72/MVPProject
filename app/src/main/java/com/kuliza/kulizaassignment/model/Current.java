package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Current Model
 */
public class Current extends RealmObject {

    @SerializedName("temp_c")
    private Double temp_c;

    public Double getTemp_c() {
        return temp_c;
    }
}
