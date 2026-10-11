package com.atharok.btremote.data.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.atharok.btremote.common.extensions.parcelable
import com.atharok.btremote.common.utils.checkBluetoothConnectPermission
import com.atharok.btremote.common.utils.checkBluetoothScanPermission
import com.atharok.btremote.domain.entities.DeviceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothScanner(
    private val context: Context,
    private val adapter: BluetoothAdapter?
) {
    private val _scannedDevices = MutableStateFlow<List<DeviceEntity>>(emptyList())
    val scannedDevicesFlow: StateFlow<List<DeviceEntity>> = _scannedDevices.asStateFlow()

    // ---- BroadcastReceiver ----

    private val bluetoothScannerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    intent.parcelable(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)?.let { device: BluetoothDevice ->
                        if(checkBluetoothConnectPermission(this@BluetoothScanner.context)) {
                            val current = _scannedDevices.value
                            if(!current.any { it.macAddress == device.address }) {
                                val newDevice = DeviceEntity(
                                    name = device.name ?: "null",
                                    macAddress = device.address ?: "null",
                                    category = device.bluetoothClass.majorDeviceClass
                                )
                                _scannedDevices.value = current + newDevice
                            }
                        }
                    }
                }
            }
        }
    }

    fun registerBluetoothScannerReceiver() {
        _scannedDevices.value = emptyList()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(bluetoothScannerReceiver, filter)
    }

    fun unregisterBluetoothScannerReceiver() {
        try {
            context.unregisterReceiver(bluetoothScannerReceiver)
        } catch (e: java.lang.RuntimeException) {
            Log.e("unregisterReceiver()", "Receiver already unregister: ${e.message ?: e.toString()}")
        }
    }

    fun startDiscoveryDevices(): Boolean {
        return if (checkBluetoothScanPermission(context)) {
            adapter?.startDiscovery() == true
        } else false
    }

    fun cancelDiscoveryDevices(): Boolean {
        return if (checkBluetoothScanPermission(context)) {
            adapter?.cancelDiscovery() == true
        } else false
    }
}