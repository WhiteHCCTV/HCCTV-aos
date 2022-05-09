package com.example.hcctv.model.repository

import android.app.Application
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.database.DeviceDao
import com.example.hcctv.model.database.DeviceDatabase

class DeviceRepositoryImpl(application: Application) : DeviceRepository {
    private val deviceDao by lazy {
        DeviceDatabase.getInstance(application)!!.deviceDao()
    }

    override suspend fun insertDevice(address: String) =
        deviceDao.insertDevice(Device(id = null, ipAddress = address))

    override suspend fun updateDevice(address: String) =
        deviceDao.updateDevice(Device(id = null, ipAddress = address))

    override suspend fun deleteDevice(address: String) =
        deviceDao.deleteDevice(Device(id = null, ipAddress = address))

    override fun getAllDevices(): List<Device> = deviceDao.getAllDevices()
}