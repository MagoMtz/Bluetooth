package com.mago.btscanner.neardevices.view;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mago.btscanner.R;
import com.mago.btscanner.clouddevices.view.CloudDevicesActivity;
import com.mago.btscanner.databinding.ActivityNearDevicesBinding;
import com.mago.btscanner.db.entities.Device;
import com.mago.btscanner.adapter.DevicesAdapter;
import com.mago.btscanner.adapter.OnItemClickListener;
import com.mago.btscanner.lib.Flags;
import com.mago.btscanner.neardevices.interactor.BTScannerBroadcastReceiver;
import com.mago.btscanner.neardevices.interactor.DeviceFoundListener;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenter;
import com.mago.btscanner.neardevices.presenter.NearDevicesPresenterImpl;

import java.util.ArrayList;

public class NearDevicesActivity extends AppCompatActivity implements OnItemClickListener, NearDevicesView, DeviceFoundListener {
    private ActivityNearDevicesBinding view;
    private ArrayList<Device> deviceArrayList;
    private DevicesAdapter devicesAdapter;
    private NearDevicesPresenter presenter;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_near_devices);
        setSupportActionBar(view.toolbar);

        presenter = new NearDevicesPresenterImpl(this, this);

        initReceiver();
        setupRecyclerView();
        scanForNearDevices();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.cancelDiscovery();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.saveStoredDevices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        presenter.cancelDiscovery();
        presenter.onDestroy();
    }

    private void setupRecyclerView() {
        deviceArrayList = new ArrayList<>();
        devicesAdapter = new DevicesAdapter(deviceArrayList, this, this);

        view.contentNearDevices.nearDevicesList.setLayoutManager(new LinearLayoutManager(this));
        view.contentNearDevices.nearDevicesList.setAdapter(devicesAdapter);
    }

    private void initReceiver(){
        receiver = new BTScannerBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case Flags.IntentCodes.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK)
                    scanForNearDevices();

                if (resultCode == RESULT_CANCELED)
                    showEnableBTMsg();

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Flags.IntentCodes.REQUEST_ACCESS_COARSE_LOCATION:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    scanForNearDevices();

                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
                    showGrantPermissionMsg();

                break;
        }
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
                scanForNearDevices();
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
        view.progressHorizontal.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(android.R.color.holo_orange_dark), PorterDuff.Mode.SRC_IN);
        presenter.cancelDiscovery();
        presenter.saveDevice(device);
    }

    @Override
    public void showProgressBar() {
        view.progressHorizontal.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        view.progressHorizontal.setVisibility(View.GONE);
    }

    @Override
    public void showBluetoothErrMsg() {
        Snackbar.make(view.getRoot(), getString(R.string.activity_near_devices_bt_unavailable), Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void showSavedSuccessfulMsg(Device device) {
        String msg;
        msg = String.format(getString(R.string.activity_near_devices_saved_successful), device.getName());
        Snackbar.make(view.getRoot(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSavingErrorMsg(String errMsg) {
        String serverError = String.format(getString(R.string.activity_near_devices_saving_error), errMsg);
        Snackbar.make(view.getRoot(), serverError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void enableBluetooth() {
        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBTIntent, Flags.IntentCodes.REQUEST_ENABLE_BT);
    }

    @Override
    public void addDeviceToList(Device device) {
        deviceArrayList.add(device);
        devicesAdapter.notifyDataSetChanged();
    }

    private void navigateToCloudDevices(){
        startActivity(new Intent(this, CloudDevicesActivity.class));
    }

    private void showEnableBTMsg(){
        Snackbar.make(view.getRoot(), getString(R.string.activity_near_devices_bt_not_active_msg), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.activate, (v) -> enableBluetooth())
                .show();
    }

    private void showGrantPermissionMsg() {
        Snackbar.make(view.getRoot(), getString(R.string.activity_near_devices_permission_not_granted), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.activate, (v) -> scanForNearDevices())
                .show();
    }

    private void scanForNearDevices(){
        view.progressHorizontal.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        deviceArrayList.clear();
        devicesAdapter.notifyDataSetChanged();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Flags.IntentCodes.REQUEST_ACCESS_COARSE_LOCATION);
        }else {
            presenter.getNearDevices();
        }
    }

}
