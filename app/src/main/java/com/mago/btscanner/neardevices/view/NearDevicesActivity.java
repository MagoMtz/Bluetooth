package com.mago.btscanner.neardevices.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

        presenter = new NearDevicesPresenterImpl(this);

        initReceiver();
        setupRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case Flags.IntentCodes.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    scanForNearDevices();
                }
                if (resultCode == RESULT_CANCELED) {
                    showEnableBTMsg();
                }
                break;
        }
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
        presenter.saveDevice(device);
    }

    private void navigateToCloudDevices(){
        startActivity(new Intent(this, CloudDevicesActivity.class));
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
    public void showSavedSuccessfulMsg() {
        Snackbar.make(view.getRoot(), getString(R.string.activity_near_devices_saved_successful), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSavingErrorMsg(String errMsg) {
        String serverError = String.format(getString(R.string.activity_near_devices_saving_error), errMsg);
        Snackbar.make(view.getRoot(), serverError, Snackbar.LENGTH_SHORT).show();
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

    private void showEnableBTMsg(){
        Snackbar.make(view.getRoot(), getString(R.string.activity_near_devices_bt_not_active_msg), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.activate, (v) -> enableBluetooth())
                .show();
    }

    private void scanForNearDevices(){
        deviceArrayList.clear();
        devicesAdapter.notifyDataSetChanged();

        presenter.getNearDevices();
    }


}
