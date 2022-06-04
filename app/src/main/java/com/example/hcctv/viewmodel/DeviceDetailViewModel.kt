package com.example.hcctv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.hcctv.model.repository.DeviceDetailRepositoryImpl

class DeviceDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeviceDetailRepositoryImpl(application)

    suspend fun getImageCount(deviceId: Int): Int {
        return repository.getImageCount(deviceId)
    }

    suspend fun insertImageCount(deviceId: Int, count: Int) {
        repository.insertImageCount(deviceId, count)
    }

    suspend fun updateImageCount(deviceId: Int, count: Int) {
        repository.updateImageCount(deviceId, count)
    }
}