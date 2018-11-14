package com.mago.btscanner.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mago.btscanner.db.dao.DevicesDAO;
import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 14/11/18.
 */
@Database(version = DevicesContract.DeviceData.DB_VERSION,
        entities = Device.class,
        exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    abstract public DevicesDAO devicesDAO();

    public static AppDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DevicesContract.DeviceData.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
