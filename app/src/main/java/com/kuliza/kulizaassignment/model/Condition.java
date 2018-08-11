package com.kuliza.kulizaassignment.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Condition Model Class
 */
public class Condition extends RealmObject {

    @SerializedName("code")
    private Integer code;
    @SerializedName("text")
    private String text;
    @SerializedName("icon")
    private String icon;

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }
}
