package com.mago.btscanner.neardevices.interactor;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mago.btscanner.db.entities.Device;

public class BTScannerBroadcastReceiver extends BroadcastReceiver {
    private DeviceFoundListener deviceFoundListener;

    public BTScannerBroadcastReceiver(DeviceFoundListener deviceFoundListener) {
        this.deviceFoundListener = deviceFoundListener;
    }

    public BTScannerBroadcastReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)){
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int strength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
            Device dev = new Device();
            dev.setAddress(device.getAddress());
            dev.setName(device.getName());
            dev.setStrength(strength +"db");
            deviceFoundListener.addDeviceToList(dev);
        }
    }

}
