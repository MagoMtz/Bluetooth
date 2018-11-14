package com.mago.btscanner.neardevices.interactor;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.lib.APIServiceRetrofit;
import com.mago.btscanner.lib.RetrofitAPIUtils;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenter;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import retrofit2.HttpException;

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
        //device.setCreatedAt("2018-11-10T19:33:30.205Z");
        //listener.onSuccess(device, true);

        //device.setAddress("6C:96:CF:DF:51:F8");
        Log.i("Interactor", "saveDevice() "+device.toJSON());

        APIServiceRetrofit serviceRetrofit = RetrofitAPIUtils.getServiceRetrofit();
        serviceRetrofit.addDevice()
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
                        Log.e("NearInteractor", "http "+((HttpException) e).code());
                        Log.e("NearInteractor", "http "+((HttpException) e).message());
                        Log.e("NearInteractor", "http "+((HttpException) e).response().message());
                        Log.e("NearInteractor", "http "+((HttpException) e).response().body());
                        Log.e("NearInteractor", "http "+((HttpException) e).response().raw().toString());
                        e.printStackTrace();
                        listener.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    private boolean isBTEnabled(){
        if (!bluetoothAdapter.isEnabled()){
            presenter.enableBluetooth();
            return false;
        }
        return true;
    }

}
