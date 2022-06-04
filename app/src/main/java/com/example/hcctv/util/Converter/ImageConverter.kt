package com.example.hcctv.util.Converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class ImageConverter {
    // 바이트 -> 비트맵 변환
    fun ByteArray.toBitmap(): Bitmap {
        return BitmapFactory.decodeByteArray(this, 0, size)
    }

    // 비트맵 -> 바이트 변환
    fun Bitmap.toByte(): ByteArray {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG, 50, this)
            return toByteArray()
        }
    }
}