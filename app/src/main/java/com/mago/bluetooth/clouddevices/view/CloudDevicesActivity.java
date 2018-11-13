package com.mago.bluetooth.clouddevices.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mago.bluetooth.R;
import com.mago.bluetooth.adapter.DevicesAdapter;
import com.mago.bluetooth.clouddevices.presenter.CloudDevicesPresenter;
import com.mago.bluetooth.clouddevices.presenter.CloudDevicesPresenterImpl;
import com.mago.bluetooth.databinding.ActivityCloudDevicesBinding;
import com.mago.bluetooth.db.entities.Device;

import java.util.ArrayList;

public class CloudDevicesActivity extends AppCompatActivity implements CloudDevicesView {
    private ActivityCloudDevicesBinding view;
    private ArrayList<Device> deviceArrayList;
    private CloudDevicesPresenter presenter;
    private DevicesAdapter devicesAdapter;
    private boolean ascendant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_cloud_devices);
        setSupportActionBar(view.toolbar);

        presenter = new CloudDevicesPresenterImpl(this);

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCloudDevices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupRecyclerView() {
        deviceArrayList = new ArrayList<>();
        devicesAdapter = new DevicesAdapter(deviceArrayList, null, this);

        view.contentCloudDevices.cloudDevicesList.setLayoutManager(new LinearLayoutManager(this));
        view.contentCloudDevices.cloudDevicesList.setAdapter(devicesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cloud_devices_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_order_list:
                ascendant = !ascendant;
                presenter.reorderList(new ArrayList<>(deviceArrayList), ascendant);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgressBar() {
        view.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        view.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void showNoDevicesMsg() {
        view.tvNoDevicesFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDevicesMsg() {
        view.tvNoDevicesFound.setVisibility(View.GONE);
    }

    @Override
    public void showServerError(String errMsg) {
        Snackbar.make(view.getRoot(), errMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCloudDevices(ArrayList<Device> devices) {
        deviceArrayList.clear();
        deviceArrayList.addAll(devices);
        devicesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOrderedDevices(ArrayList<Device> orderedDevices) {
        deviceArrayList.clear();
        deviceArrayList.addAll(orderedDevices);
        devicesAdapter.notifyDataSetChanged();
    }
}
