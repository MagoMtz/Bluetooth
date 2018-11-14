package com.mago.btscanner.neardevices.interactor;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mago.btscanner.db.AppDataBase;
import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.HttpException;

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

        /*
        APIServiceRetrofit serviceRetrofit = RetrofitAPIUtils.getServiceRetrofit();
        serviceRetrofit.addDevice(device.toJSON())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Device>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Device device) {
                        Log.i("NearInteractor", "onNext() "+device.toJSON());
                        device.setCreatedAt(device.getCreatedAt());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("NearInteractor", "error "+e.getLocalizedMessage());
                        try {
                            Log.e("NearInteractor", "http " + ((HttpException) e).code());
                            Log.e("NearInteractor", "http " + ((HttpException) e).message());
                            Log.e("NearInteractor", "http " + ((HttpException) e).response().message());
                            Log.e("NearInteractor", "http " + ((HttpException) e).response().body());
                            Log.e("NearInteractor", "http " + ((HttpException) e).response().raw().toString());
                            Log.e("NearInteractor", "http headers " + ((HttpException) e).response().raw().headers().toString());
                        }catch (Exception e1){

                        }
                        e.printStackTrace();
                        listener.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

                */
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
    public void saveStoredDevices() {
        Device[] devices = dataBase.devicesDAO().allDevices();
        if (devices.length <=0 )
            return;

        for (Device device : devices){
            Log.i("devicesSaved", device.toJSON());
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
