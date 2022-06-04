package com.example.hcctv.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hcctv.model.data.ImageCount

@Dao
interface ImageCountDao {
    @Insert
    suspend fun insertImageCount(imageCount : ImageCount)

    @Insert
    suspend fun updateImageCount(imageCount : ImageCount)

    @Query("SELECT imageCount FROM ImageCount WHERE deviceId = :deviceId")
    suspend fun getImageCount(deviceId: Int): Int


}