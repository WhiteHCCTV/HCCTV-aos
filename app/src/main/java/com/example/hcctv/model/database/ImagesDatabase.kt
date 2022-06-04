package com.example.hcctv.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hcctv.model.data.Device
import com.example.hcctv.model.data.ImageCount

@Database(entities = [Device::class, ImageCount::class], version = 1)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImageCountDao

    companion object {
        private var instance: ImagesDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ImagesDatabase? {
            if (instance == null) {
                synchronized(ImagesDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ImagesDatabase::class.java,
                        "images_database"
                    ).build()
                }
            }
            return instance
        }
    }
}