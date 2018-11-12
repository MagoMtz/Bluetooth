package com.mago.bluetooth.neardevices.adapter;

import android.content.Context;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mago.bluetooth.R;
import com.mago.bluetooth.databinding.AdapterDevicesBinding;


import com.mago.bluetooth.db.entities.Device;

import java.util.ArrayList;

/**
 * Created by jorgemartinez on 12/11/18.
 */
public class NearDevicesAdapter extends RecyclerView.Adapter<NearDevicesAdapter.ViewHolder> {
    private ArrayList<Device> devicesList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public NearDevicesAdapter(ArrayList<Device> devicesList, OnItemClickListener onItemClickListener, Context context) {
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
        String deviceStrength = String.format(context.getString(R.string.activity_near_devices_adapter_strength), device.getStrength());

        viewHolder.viewBinding.tvDeviceName.setText(deviceName);
        viewHolder.viewBinding.tvDeviceAddress.setText(deviceAddress);
        viewHolder.viewBinding.tvIntensity.setText(deviceStrength);
        viewHolder.setOnItemClickListener(device, onItemClickListener);
        viewHolder.setIntensityIcon(device.getStrength());

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
            if ((strength >= 0) && (strength <= 25)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_poor);
            } else
            if ((strength >= 26) && (strength <= 50)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_ok);
            } else
            if ((strength >= 51) && (strength <= 75)) {
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_great);
            } else
            if ((strength >= 76) && (strength <= 100)){
                viewBinding.ivIntensity.setImageResource(R.drawable.ic_signal_perfect);
            }
        }

    }
}
