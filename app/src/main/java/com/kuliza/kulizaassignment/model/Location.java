package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Location Model
 */
public class Location extends RealmObject {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
