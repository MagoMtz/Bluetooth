package com.mago.bluetooth.lib;

/**
 * Created by jorgemartinez on 13/11/18.
 */
public class RetrofitAPIUtils {
    private static final String BASE_URL = "https://grin-bluetooth-api.herokuapp.com/";

    private RetrofitAPIUtils(){}

    public static APIServiceRetrofit getServiceRetrofit() {
        return RetrofitClient.getClient(BASE_URL).create(APIServiceRetrofit.class);
    }
}
