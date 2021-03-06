package com.mago.btscanner.clouddevices.interactor;

import com.mago.btscanner.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface CloudDevicesInteractor {
    void getCloudDevices(OnEventListener listener);
    void reorderList(ArrayList<Device> devices, boolean ascendant);

    interface OnEventListener {
        void onError(String errMsg);
        void onSuccess(ArrayList<Device> devices);
    }
}
