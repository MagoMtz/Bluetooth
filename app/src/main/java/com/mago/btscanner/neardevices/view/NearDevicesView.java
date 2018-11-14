package com.mago.btscanner.neardevices.view;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface NearDevicesView {
    void showProgressBar();
    void hideProgressBar();
    void showBluetoothErrMsg();
    void showSavedSuccessfulMsg(String deviceName);
    void showSavingErrorMsg(String errMsg);

    void enableBluetooth();
}
