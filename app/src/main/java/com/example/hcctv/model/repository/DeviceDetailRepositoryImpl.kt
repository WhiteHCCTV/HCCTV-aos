package com.example.hcctv.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.hcctv.model.data.ImageCount
import com.example.hcctv.model.database.ImagesDatabase

class DeviceDetailRepositoryImpl(application: Application) : DeviceDetailRepository {
    private val imageCountDao by lazy {
        ImagesDatabase.getInstance(application)!!.imagesDao()
    }

    override suspend fun insertImageCount(deviceId: Int, count: Int) {
        imageCountDao.insertImageCount(ImageCount(id = null, deviceId = deviceId, imageCount = count))
    }

    override suspend fun updateImageCount(deviceId: Int, count: Int) {
        imageCountDao.updateImageCount(ImageCount(id = null, deviceId = deviceId, imageCount = count))
    }

    override suspend fun getImageCount(deviceId: Int): Int {
        return imageCountDao.getImageCount(deviceId)
    }
}