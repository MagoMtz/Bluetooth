package com.mago.btscanner.neardevices.interactor;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mago.btscanner.db.AppDataBase;
import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public class NearDevicesInteractorImpl implements NearDevicesInteractor {
    private NearDevicesPresenter presenter;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private AppDataBase dataBase;

    private static final String ADD_URL = "https://grin-bluetooth-api.herokuapp.com/add";

    public NearDevicesInteractorImpl(NearDevicesPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        dataBase = AppDataBase.getINSTANCE(context);
    }

    @Override
    public void saveDevice(Device device, OnEventListener listener) {
        sendWithVolley(device, listener, false);
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
        if (bluetoothAdapter != null)
            bluetoothAdapter.cancelDiscovery();
    }

    @Override
    public void saveStoredDevices(OnEventListener listener) {
        Device[] devices = dataBase.devicesDAO().allDevices();
        if (devices.length <= 0 )
            return;

        for (Device device : devices){
            sendWithVolley(device, listener, true);
        }
    }

    private boolean isBTEnabled(){
        if (!bluetoothAdapter.isEnabled()){
            presenter.enableBluetooth();
            return false;
        }
        return true;
    }

    private void sendWithVolley(Device device, OnEventListener listener, boolean isStoredDevice){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                com.android.volley.Request.Method.POST,
                ADD_URL,
                null,
                (response) -> {
                    if (isStoredDevice){
                        dataBase.devicesDAO().deleteDevice(device);
                        return;
                    }

                    listener.onSuccess(new Device().fromJSON(response.toString()));
                },
                (volleyError) -> {
                    if (isStoredDevice){
                        return;
                    }

                    dataBase.devicesDAO().insertDevice(device);

                    if (volleyError.networkResponse == null){
                        listener.onError("Timeout");
                        return;
                    }
                    listener.onError("Server error: "+volleyError.networkResponse.statusCode);
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try{
                    return device.toJSON().getBytes("utf-8");
                }catch (Exception e){
                    e.printStackTrace();
                }
                return super.getBody();
            }
        };
        requestQueue.add(request);
    }

}
