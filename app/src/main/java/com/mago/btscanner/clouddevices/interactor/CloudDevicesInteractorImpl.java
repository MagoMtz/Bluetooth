package com.mago.btscanner.clouddevices.interactor;

import android.util.Log;

import com.mago.btscanner.clouddevices.presenter.CloudDevicesPresenter;
import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.lib.APIServiceRetrofit;
import com.mago.btscanner.lib.RetrofitAPIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jorgemartinez on 13/11/18.
 */
public class CloudDevicesInteractorImpl implements CloudDevicesInteractor{
    private static final String TAG = CloudDevicesInteractorImpl.class.getSimpleName();
    private CloudDevicesPresenter presenter;

    private ArrayList<Device> devicesList;

    public CloudDevicesInteractorImpl(CloudDevicesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getCloudDevices(OnEventListener listener) {
        APIServiceRetrofit serviceRetrofit = RetrofitAPIUtils.getServiceRetrofit();
        serviceRetrofit.getDevices()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Device>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Device> devices) {
                        devicesList = new ArrayList<>(devices);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError() "+e.getLocalizedMessage());
                        e.printStackTrace();
                        listener.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.onSuccess(devicesList);
                    }
                });

    }

    @Override
    public void reorderList(ArrayList<Device> devices, boolean ascendant) {
        Collections.sort(devices, (device1, device2) -> {
                    if (ascendant)
                        return device1.getCreatedAt().compareTo(device2.getCreatedAt());
                    else
                        return device2.getCreatedAt().compareTo(device1.getCreatedAt());
                }
        );

        presenter.updateList(devices);
    }
}
