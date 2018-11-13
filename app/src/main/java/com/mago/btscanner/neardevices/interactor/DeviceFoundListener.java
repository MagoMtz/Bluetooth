package com.mago.btscanner.neardevices.interactor;

import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface DeviceFoundListener {
    void addDeviceToList(Device device);
}
