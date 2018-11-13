package com.mago.bluetooth.clouddevices.presenter;

import com.mago.bluetooth.clouddevices.interactor.CloudDevicesInteractor;
import com.mago.bluetooth.clouddevices.interactor.CloudDevicesInteractorImpl;
import com.mago.bluetooth.clouddevices.view.CloudDevicesView;
import com.mago.bluetooth.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public class CloudDevicesPresenterImpl implements CloudDevicesPresenter, CloudDevicesInteractor.OnEventListener{
    private CloudDevicesInteractor interactor;
    private CloudDevicesView view;

    public CloudDevicesPresenterImpl(CloudDevicesView view) {
        this.view = view;
        this.interactor = new CloudDevicesInteractorImpl(this);
    }

    @Override
    public void getCloudDevices() {
        view.showProgressBar();
        view.hideNoDevicesMsg();
        interactor.getCloudDevices(this);
    }

    @Override
    public void reorderList(ArrayList<Device> devices, boolean ascendant) {
        view.showProgressBar();
        view.hideNoDevicesMsg();
        interactor.reorderList(devices, ascendant);
    }

    @Override
    public void updateList(ArrayList<Device> orderedDevices) {
        view.hideProgressBar();
        if (orderedDevices.size() <= 0) {
            view.showNoDevicesMsg();
            return;
        } else {
            view.hideNoDevicesMsg();
        }

        view.showOrderedDevices(orderedDevices);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onError(String errMsg) {
        view.hideProgressBar();
        if (!errMsg.isEmpty()) {
            view.showNoDevicesMsg();
            view.showServerError(errMsg);
        } else {
            view.hideNoDevicesMsg();
        }

    }

    @Override
    public void onSuccess(ArrayList<Device> devices) {
        view.hideProgressBar();
        if (devices.size() <= 0) {
            view.showNoDevicesMsg();
            return;
        } else {
            view.hideNoDevicesMsg();
        }

        view.showCloudDevices(devices);
    }
}
