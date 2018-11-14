package com.mago.btscanner.neardevices.interactor;

import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface NearDevicesInteractor {
    void saveDevice(Device device, OnEventListener listener);

    void getNearDevices();
    void cancelDiscovery();
    void saveStoredDevices();

    interface OnEventListener {
        void onError(String errMsg);
        void onSuccess(Device device);
    }
}
