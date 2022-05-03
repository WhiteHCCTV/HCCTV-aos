package com.example.hcctv.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.repository.DeviceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceViewModel(application : Application) : AndroidViewModel(application) {
    private val _deviceItemList: MutableLiveData<List<Device>> = MutableLiveData()
    private val repository = DeviceRepositoryImpl(application)

    val deviceItemList: LiveData<List<Device>>
        get() = _deviceItemList

    fun getDeviceItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _deviceItemList.value = repository.getAllDevices()
            }
        }
    }
}