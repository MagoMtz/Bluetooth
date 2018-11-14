package com.mago.btscanner.neardevices.presenter;

import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface NearDevicesPresenter {
    void saveDevice(Device device);
    void getNearDevices();

    void bluetoothError();
    void enableBluetooth();
    void cancelDiscovery();
    void saveStoredDevices();

    void onDestroy();
}
