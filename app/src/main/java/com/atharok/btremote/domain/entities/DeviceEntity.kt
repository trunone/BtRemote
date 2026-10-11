package com.atharok.btremote.domain.entities

import com.atharok.btremote.R

data class DeviceEntity(
    val name: String,
    val macAddress: String,
    val address: String = macAddress,
    private val category: Int = 0
) {
    val iconResId: Int = R.drawable.notification_icon
}