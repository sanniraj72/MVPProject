package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Day Model Class
 */
public class Day extends RealmObject {

    @SerializedName("mintemp_c")
    private Double mintemp_c;
    @SerializedName("maxtemp_c")
    private Double maxtemp_c;
    @SerializedName("condition")
    private Condition condition;

    public Double getMintemp_c() {
        return mintemp_c;
    }

    public Double getMaxtemp_c() {
        return maxtemp_c;
    }

    public Condition getCondition() {
        return condition;
    }
}
