package com.atharok.btremote.domain.usecases

import com.atharok.btremote.common.utils.KEYBOARD_REPORT_ID
import com.atharok.btremote.common.utils.REMOTE_REPORT_ID
import com.atharok.btremote.domain.entities.DeviceHidConnectionState
import com.atharok.btremote.domain.repositories.BluetoothRepository
import com.atharok.btremote.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class BluetoothHidServiceUseCase(
    private val bluetoothRepository: BluetoothRepository,
    private val dataStoreRepository: DataStoreRepository
) {

    fun startHidProfile() {
        val autoConnectDeviceAddress: String
        runBlocking {
            autoConnectDeviceAddress = dataStoreRepository.getAutoConnectDeviceAddressFlow().first()
        }
        bluetoothRepository.startHidProfile(autoConnectDeviceAddress)
    }

    fun stopHidProfile() {
        bluetoothRepository.stopHidProfile()
    }

    fun disconnectDevice(): Boolean {
        return bluetoothRepository.disconnectDevice()
    }

    fun getDeviceHidConnectionState(): StateFlow<DeviceHidConnectionState> {
        return bluetoothRepository.getDeviceHidConnectionState()
    }

    fun sendReport(id: Int, bytes: ByteArray): Boolean {
        return bluetoothRepository.sendReport(id, bytes)
    }

    fun sendRemoteReport(bytes: ByteArray): Boolean {
        return sendReport(REMOTE_REPORT_ID, bytes)
    }

    fun sendKeyboardReport(bytes: ByteArray): Boolean {
        return sendReport(KEYBOARD_REPORT_ID, bytes)
    }

    fun useEnterForSelection(): Boolean = runBlocking {
        dataStoreRepository.remoteSettingsFlow.first().useEnterForSelection
    }

    fun showRemoteButtonsInNotification(): Flow<Boolean> = dataStoreRepository.showRemoteButtonsInNotification()
}