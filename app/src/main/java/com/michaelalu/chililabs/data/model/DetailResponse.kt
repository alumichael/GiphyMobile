package com.michaelalu.chililabs.data.model

data class DetailResponse(
    val data: GifData
)

data class GifData(
    val id: String?,
    val title: String?,
    val username: String?,
    val rating: String?,
    val import_datetime: String?,
    val images: GifImages?
)

data class GifImages(
    val original: GifOriginal?
)

data class GifOriginal(
    val url: String?
)
