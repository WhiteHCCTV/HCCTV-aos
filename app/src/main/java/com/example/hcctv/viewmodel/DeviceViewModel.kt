package com.example.hcctv.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.repository.DeviceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceViewModel(application: Application) : AndroidViewModel(application) {
    private val _deviceItemList: MutableLiveData<List<Device>> = MutableLiveData()
    private val repository = DeviceRepositoryImpl(application)

    val deviceItemList: LiveData<List<Device>>
        get() = _deviceItemList

    fun getDeviceItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _deviceItemList.postValue(repository.getAllDevices())
            }
        }
    }

    fun insertDevice(address : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertDevice(address)
            }
        }
    }

    fun updateDevice(address : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateDevice(address)
            }
        }
    }

    fun deleteDevice() {

    }


    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DeviceViewModel(application) as T
        }
    }
}