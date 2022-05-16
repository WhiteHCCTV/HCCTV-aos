package com.example.hcctv.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.repository.DeviceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeviceRepositoryImpl(application)
    private val _deviceItemList = repository.getAllDevices()

    val deviceItemList: LiveData<List<Device>>
        get() = _deviceItemList

    fun getAllDevices(): LiveData<List<Device>> {
        return deviceItemList
    }

    suspend fun insertDevice(address: String) {
        repository.insertDevice(address)
    }

    suspend fun updateDevice(address: String) {
        repository.updateDevice(address)
    }

    suspend fun deleteDevice(address: String) {
        repository.deleteDevice(address)
    }
}