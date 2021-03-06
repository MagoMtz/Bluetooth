package com.mago.btscanner.clouddevices.presenter;

import com.mago.btscanner.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface CloudDevicesPresenter {
    void getCloudDevices();
    void reorderList(ArrayList<Device> devices, boolean ascendant);

    void updateList(ArrayList<Device> orderedDevices);

    void onDestroy();
}
