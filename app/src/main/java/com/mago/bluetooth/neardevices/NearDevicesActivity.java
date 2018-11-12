package com.mago.bluetooth.neardevices;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mago.bluetooth.R;
import com.mago.bluetooth.clouddevices.CloudDevicesActivity;
import com.mago.bluetooth.databinding.ActivityNearDevicesBinding;
import com.mago.bluetooth.db.entities.Device;
import com.mago.bluetooth.adapter.DevicesAdapter;
import com.mago.bluetooth.adapter.OnItemClickListener;

import java.util.ArrayList;


public class NearDevicesActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityNearDevicesBinding view;
    private ArrayList<Device> deviceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_near_devices);
        setSupportActionBar(view.toolbar);

        setupRecyclerView();
        mockDevices();
    }

    private void mockDevices(){
        Device device = new Device();
        device.setName("Jorge");
        device.setStrength(50);
        device.setAddress("6F:10:18:A2:8B:A2");

        deviceArrayList.add(device);
    }

    private void setupRecyclerView() {
        deviceArrayList = new ArrayList<>();
        DevicesAdapter adapter = new DevicesAdapter(deviceArrayList, this, this);

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
            case R.id.action_cloud_list:
                navigateToCloudDevices();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Device device) {
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
    }

    private void navigateToCloudDevices(){
        startActivity(new Intent(this, CloudDevicesActivity.class));
    }
}
