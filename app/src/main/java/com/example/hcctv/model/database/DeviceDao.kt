package com.example.hcctv.model.database

import androidx.room.*
import com.example.hcctv.model.data.Device

@Dao
interface DeviceDao {
    @Insert
    suspend fun insertDevice(device: Device)

    @Update
    suspend fun updateDevice(device: Device)

    @Delete
    suspend fun deleteDevice(device: Device)

    @Query("SELECT * FROM Device")
    fun getAllDevices(): List<Device>
}