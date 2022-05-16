package com.example.hcctv.model.repository

import androidx.lifecycle.LiveData
import com.example.hcctv.model.data.Device

interface DeviceRepository {
    suspend fun insertDevice(address: String)

    suspend fun updateDevice(address: String)

    suspend fun deleteDevice(address: String)

    fun getAllDevices(): LiveData<List<Device>>
}