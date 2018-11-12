package com.mago.bluetooth.neardevices;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mago.bluetooth.R;
import com.mago.bluetooth.databinding.ActivityNearDevicesBinding;
import com.mago.bluetooth.db.entities.Device;
import com.mago.bluetooth.neardevices.adapter.NearDevicesAdapter;
import com.mago.bluetooth.neardevices.adapter.OnItemClickListener;

import java.util.ArrayList;


public class NearDevicesActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityNearDevicesBinding view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_near_devices);
        setSupportActionBar(view.toolbar);

        ArrayList<Device> deviceArrayList = new ArrayList<>();
        Device device = new Device();
        device.setAddress("6C:96:CF:DF:51:F8");
        device.setName("Jorge’s MacBook Pro");
        device.setStrength(10);
        deviceArrayList.add(device);

        Device device1 = new Device();
        device1.setStrength(30);
        device1.setName("1");
        deviceArrayList.add(device1);

        Device device2 = new Device();
        device2.setStrength(60);
        device2.setName("2");
        deviceArrayList.add(device2);

        Device device3 = new Device();
        device3.setName("3");
        device3.setStrength(90);
        deviceArrayList.add(device3);

        NearDevicesAdapter adapter = new NearDevicesAdapter(deviceArrayList, this, this);

        view.contentNearDevices.nearDevicesList.setLayoutManager(new LinearLayoutManager(this));
        view.contentNearDevices.nearDevicesList.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.near_devices_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh_list:
                Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Device device) {
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
    }
}
