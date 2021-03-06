package com.android.kudago_kotlin.domain

data class Events(
    val count: Int,
    val previousPage: String?,
    val nextPage: String?,
    val results: List<Event>
) {

    data class Event(
        val id: Long,
        val images: List<Image>?,
        val title: String,
        val description: String,
        val place: Place?,
        val dates: String?,
        val price: String?
    ) {

        data class Image(
            val imageUrl: String
        )

        data class Place(
            val id: Long,
            val title: String
        )

        data class Date(
            val start: Long?,
            val end: Long?
        )
    }
}