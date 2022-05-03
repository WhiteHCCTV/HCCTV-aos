package com.example.hcctv.model.repository

import com.example.hcctv.model.data.Device

interface DeviceRepository {
    suspend fun insertDevice(device: Device)

    suspend fun updateDevice(device: Device)

    suspend fun deleteDevice(device: Device)

    suspend fun getAllDevices(): List<Device>
}