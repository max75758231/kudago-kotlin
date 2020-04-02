package com.android.kudago_kotlin.domain

data class Event(
    val id: Long,
    val images: List<Image>?,
    val title: String,
    val fullDescription: String,
    val place: Place?,
    val dates: String?,
    val price: String?
) {

    data class Image(
        val imageUrl: String
    )

    data class Place(
        val id: Long,
        val title: String,
        val coords: Coords
    ) {

        data class Coords(
            val latitude: Double,
            val longitude: Double
        )
    }

    data class Date(
        val start: Long?,
        val end: Long?
    )
}