package com.mago.btscanner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mago.btscanner.R;
import com.mago.btscanner.databinding.AdapterDevicesBinding;


import com.mago.btscanner.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 12/11/18.
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {
    private ArrayList<Device> devicesList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public DevicesAdapter(ArrayList<Device> devicesList, OnItemClickListener onItemClickListener, Context context) {
        this.devicesList = devicesList;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        AdapterDevicesBinding view = AdapterDevicesBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Device device = devicesList.get(i);

        String deviceName = device.getName();
        String deviceAddress = device.getAddress();
        String deviceStrength = String.format(context.getString(R.string.activity_near_devices_adapter_strength), device.getStrengthAsInt());
        String deviceDate = device.getCreatedAt();

        viewHolder.viewBinding.tvDeviceName.setText(deviceName);
        viewHolder.viewBinding.tvDeviceAddress.setText(deviceAddress);
        viewHolder.viewBinding.tvIntensity.setText(deviceStrength);
        viewHolder.setOnItemClickListener(device, onItemClickListener);
        viewHolder.setIntensityIcon(device.getStrengthAsInt());
        if (onItemClickListener == null) {
            viewHolder.viewBinding.tvDeviceDate.setText(deviceDate);
            viewHolder.viewBinding.ivSaveDevice.setVisibility(View.GONE);
            viewHolder.viewBinding.tvDeviceDate.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewBinding.tvDeviceDate.setVisibility(View.GONE);
            viewHolder.viewBinding.ivSaveDevice.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterDevicesBinding viewBinding;

        ViewHolder(@NonNull AdapterDevicesBinding itemView) {
            super(itemView.getRoot());
            this.viewBinding = itemView;
        }

        void setOnItemClickListener(final Device device, final OnItemClickListener onItemClickListener) {
            viewBinding.ivSaveDevice.setOnClickListener( (v) -> onItemClickListener.onItemClick(device));
        }

        void setIntensityIcon(int strength) {
            if ((strength <= 0) && (strength >= -25)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_poor);
            } else
            if ((strength <= -26) && (strength >= -50)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_ok);
            } else
            if ((strength <= -51) && (strength >= -75)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_great);
            } else
            if ((strength <= -76) && (strength >= -100)){
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_perfect);
            }
        }

    }
}
