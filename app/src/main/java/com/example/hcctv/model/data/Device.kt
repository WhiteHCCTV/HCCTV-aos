package com.example.hcctv.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Device(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val address: String?,
)