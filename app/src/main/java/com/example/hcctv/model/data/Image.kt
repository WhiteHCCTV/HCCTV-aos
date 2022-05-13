package com.example.hcctv.model.data

data class Image(
    val id: Int?,
    val image: ByteArray,
    var selected : Boolean?
)