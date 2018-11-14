package com.mago.btscanner.neardevices.view;

import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface NearDevicesView {
    void showProgressBar();
    void hideProgressBar();
    void showBluetoothErrMsg();
    void showSavedSuccessfulMsg(Device device, boolean didExists);
    void showSavingErrorMsg(String errMsg);

    void enableBluetooth();
}
