package com.mago.btscanner.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mago.btscanner.db.DevicesContract;

/**
 * Created by jorgemartinez on 12/11/18.
 */
@Entity(tableName = DevicesContract.DeviceData.TABLE_NAME,
        indices = @Index(value = DevicesContract.DeviceData.DEVICE_ADDRESS))
public class Device {

    private long id;

    @ColumnInfo(name = DevicesContract.DeviceData.DEVICE_NAME)
    private String name;

    @PrimaryKey
    @ColumnInfo(name = DevicesContract.DeviceData.DEVICE_ADDRESS)
    @android.support.annotation.NonNull
    private String address;

    @ColumnInfo(name = DevicesContract.DeviceData.DEVICE_STRENGTH)
    private String strength;

    @SerializedName("created_at")
    private String createdAt;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public Device fromJSON(String json) {
        return new Gson().fromJson(json, Device.class);
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
        this.name = name == null ? "Unknown name" : name;
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
