package com.mago.btscanner.lib;

import com.mago.btscanner.db.entities.Device;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public interface APIServiceRetrofit {
    @GET("devices")
    Observable<List<Device>> getDevices();

    @POST("add")
    @Headers("Content-Type: application/json")
    Observable<Device> addDevice(@Body String deviceJSON);
}
