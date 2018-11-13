package com.mago.bluetooth.clouddevices.view;

import com.mago.bluetooth.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface CloudDevicesView {
    void showProgressBar();
    void hideProgressBar();
    void showNoDevicesMsg();
    void hideNoDevicesMsg();
    void showServerError(String errMsg);
    void showCloudDevices(ArrayList<Device> devices);
    void showOrderedDevices(ArrayList<Device> orderedDevices);
}
