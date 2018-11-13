package com.mago.bluetooth.db.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jorgemartinez on 12/11/18.
 */
public class Device {
    private long id;
    private String name;
    private String address;
    private String strength;
    @SerializedName("created_at")
    private String createdAt;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStrength() {
        return strength;
    }

    public int getStrengthAsInt() {
        String[] aux = strength.split("d");
        return Integer.parseInt(aux[0]);
    }

    public void setStrength(String strength){
        this.strength = strength;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
