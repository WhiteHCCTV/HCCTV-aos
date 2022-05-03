package com.example.hcctv.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hcctv.model.data.Device

@Database(entities = [Device::class], version = 1)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao

    companion object {
        private var instance: DeviceDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DeviceDatabase? {
            if (instance == null) {
                synchronized(DeviceDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DeviceDatabase::class.java,
                        "device_database"
                    ).build()
                }
            }
            return instance
        }
    }
}