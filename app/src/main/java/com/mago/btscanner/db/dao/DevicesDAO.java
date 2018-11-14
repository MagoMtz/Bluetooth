package com.mago.btscanner.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mago.btscanner.db.DevicesContract;
import com.mago.btscanner.db.entities.Device;

/**
 * Created by jorgemartinez on 14/11/18.
 */
@Dao
public interface DevicesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDevice(Device device);
    @Delete
    void deleteDevice(Device device);

    @Query("SELECT * FROM "+DevicesContract.DeviceData.TABLE_NAME)
    Device[] allDevices();

}
