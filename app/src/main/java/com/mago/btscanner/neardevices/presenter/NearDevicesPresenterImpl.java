package com.mago.btscanner.neardevices.presenter;

import android.content.Context;

import com.mago.btscanner.db.AppDataBase;
import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.neardevices.interactor.NearDevicesInteractor;
import com.mago.btscanner.neardevices.interactor.NearDevicesInteractorImpl;
import com.mago.btscanner.neardevices.view.NearDevicesView;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public class NearDevicesPresenterImpl implements NearDevicesPresenter, NearDevicesInteractor.OnEventListener {
    private NearDevicesView view;
    private NearDevicesInteractor interactor;

    public NearDevicesPresenterImpl(NearDevicesView view, Context context) {
        this.view = view;
        this.interactor = new NearDevicesInteractorImpl(this, context);
    }

    @Override
    public void saveDevice(Device device) {
        view.showProgressBar();
        interactor.saveDevice(device, this);
    }

    @Override
    public void getNearDevices() {
        view.showProgressBar();
        interactor.getNearDevices();
    }

    @Override
    public void bluetoothError() {
        view.hideProgressBar();
        view.showBluetoothErrMsg();
    }

    @Override
    public void enableBluetooth() {
        view.hideProgressBar();
        view.enableBluetooth();
    }

    @Override
    public void cancelDiscovery() {
        view.hideProgressBar();
        interactor.cancelDiscovery();
    }

    @Override
    public void saveStoredDevices() {
        interactor.saveStoredDevices(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        AppDataBase.destroyInstance();
    }

    @Override
    public void onError(String errMsg) {
        view.hideProgressBar();
        view.showSavingErrorMsg(errMsg);
    }

    @Override
    public void onSuccess(Device device) {
        view.hideProgressBar();
        view.showSavedSuccessfulMsg(device);
    }

}
