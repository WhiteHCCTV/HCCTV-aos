package com.example.hcctv.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageCount(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val deviceId: Int,
    val imageCount: Int,
)