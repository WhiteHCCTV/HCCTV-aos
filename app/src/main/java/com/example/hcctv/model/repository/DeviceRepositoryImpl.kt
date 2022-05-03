package com.example.hcctv.model.repository

import android.app.Application
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.database.DeviceDao
import com.example.hcctv.model.database.DeviceDatabase

class DeviceRepositoryImpl(application: Application) : DeviceRepository {
    private val deviceDao: DeviceDao
    private val allDevices: List<Device>

    init {
        val db: DeviceDatabase = DeviceDatabase.getInstance(application)!!
        deviceDao = db.deviceDao()
        allDevices = db.deviceDao().getAllDevices()
    }

    override suspend fun insertDevice(device: Device) = deviceDao.insertDevice(device)

    override suspend fun updateDevice(device: Device) = deviceDao.updateDevice(device)

    override suspend fun deleteDevice(device: Device) = deviceDao.deleteDevice(device)

    override suspend fun getAllDevices(): List<Device> = deviceDao.getAllDevices()
}