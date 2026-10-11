package com.atharok.btremote.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharok.btremote.databinding.ItemDeviceBinding
import com.atharok.btremote.domain.entities.DeviceEntity

class DeviceAdapter(
    private val onDeviceClick: (DeviceEntity) -> Unit,
    private val onUnpairClick: (DeviceEntity) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private val devices = mutableListOf<DeviceEntity>()

    fun submitList(newDevices: List<DeviceEntity>) {
        devices.clear()
        devices.addAll(newDevices)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount(): Int = devices.size

    inner class DeviceViewHolder(private val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(device: DeviceEntity) {
            binding.tvDeviceName.text = device.name
            binding.tvDeviceAddress.text = device.address
            binding.root.setOnClickListener { onDeviceClick(device) }
            binding.btnUnpair.setOnClickListener { onUnpairClick(device) }
        }
    }
}