package com.mago.bluetooth.clouddevices;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

import com.mago.bluetooth.R;
import com.mago.bluetooth.adapter.DevicesAdapter;
import com.mago.bluetooth.adapter.OnItemClickListener;
import com.mago.bluetooth.databinding.ActivityCloudDevicesBinding;
import com.mago.bluetooth.db.entities.Device;

import java.util.ArrayList;

public class CloudDevicesActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityCloudDevicesBinding view;
    private ArrayList<Device> deviceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_cloud_devices);
        setSupportActionBar(view.toolbar);

        setupRecyclerView();
        mockDevices();
    }

    private void mockDevices(){
        Device device = new Device();
        device.setName("Mago");
        device.setStrength(89);
        device.setAddress("6F:10:18:A2:8B:A2");
        device.setCreatedAt("2018-11-10T19:33:30.205Z");

        deviceArrayList.add(device);
    }

    private void setupRecyclerView() {
        deviceArrayList = new ArrayList<>();
        DevicesAdapter adapter = new DevicesAdapter(deviceArrayList, this, this);

        view.contentCloudDevices.cloudDevicesList.setLayoutManager(new LinearLayoutManager(this));
        view.contentCloudDevices.cloudDevicesList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cloud_devices_menu, menu);
        return true;
    }

    @Override
    public void onItemClick(Device device) {

    }
}
