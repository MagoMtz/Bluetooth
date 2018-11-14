package com.mago.btscanner.db;

import android.provider.BaseColumns;

/**
 * Created by jorgemartinez on 12/11/18.
 */
public class DevicesContract {

    public static abstract class DeviceData implements BaseColumns {
        public static final int DB_VERSION = 1;
        public static final String DB_NAME = "devices_db";
        public static final String TABLE_NAME = "device";
        public static final String DEVICE_ID = "id";
        public static final String DEVICE_NAME = "name";
        public static final String DEVICE_ADDRESS = "address";
        public static final String DEVICE_STRENGTH = "strength";
    }
}
