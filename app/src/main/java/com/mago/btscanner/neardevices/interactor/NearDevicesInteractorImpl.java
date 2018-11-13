package com.mago.btscanner.neardevices.interactor;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenter;

import java.util.List;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public class NearDevicesInteractorImpl implements NearDevicesInteractor {
    private NearDevicesPresenter presenter;
    private BluetoothAdapter bluetoothAdapter;

    public NearDevicesInteractorImpl(NearDevicesPresenter presenter) {
        this.presenter = presenter;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void saveDevice(Device device, OnEventListener listener) {

    }

    @Override
    public void getNearDevices() {
        if (bluetoothAdapter == null) {
            presenter.bluetoothError();
            return;
        }
        if (!isBTEnabled())
            return;

        if (bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();

        bluetoothAdapter.startDiscovery();
    }

    @Override
    public void cancelDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    private boolean isBTEnabled(){
        if (!bluetoothAdapter.isEnabled()){
            presenter.enableBluetooth();
            return false;
        }
        return true;
    }

}
