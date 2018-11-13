package com.mago.btscanner.clouddevices.view;

import com.mago.btscanner.db.entities.Device;

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
