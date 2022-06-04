package com.example.hcctv.model.repository

import androidx.lifecycle.LiveData

interface DeviceDetailRepository {
    suspend fun insertImageCount(deviceId: Int, count: Int)

    suspend fun updateImageCount(deviceId: Int, count: Int)

    suspend fun getImageCount(deviceId: Int): Int
}