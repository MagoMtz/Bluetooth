package com.mago.btscanner.neardevices.presenter;

import android.content.BroadcastReceiver;

import com.mago.btscanner.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface NearDevicesPresenter {
    void saveDevice(Device device);
    void getNearDevices();

    void bluetoothError();
    void enableBluetooth();
    void cancelDiscovery();

    void onDestroy();
}