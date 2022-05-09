package com.example.hcctv.model.repository

import com.example.hcctv.model.data.Device

interface DeviceRepository {
    suspend fun insertDevice(address : String)

    suspend fun updateDevice(address : String)

    suspend fun deleteDevice(address : String)

    fun getAllDevices(): List<Device>
}