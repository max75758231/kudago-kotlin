package com.android.kudago_kotlin.model.data.server.entity

import com.android.kudago_kotlin.util.DateUtil
import com.android.kudago_kotlin.domain.Event

data class Event(
    val id: Long,
    val images: List<Image>?,
    val title: String,
    val body_text: String,
    val place: Place?,
    val dates: List<Date>?,
    val price: String
) {

    data class Image(
        val image: String
    ) {

        fun toDomainModel() = Event.Image(
            image
        )
    }

    data class Place(
        val id: Long,
        val title: String,
        val coords: Coords
    ) {

        data class Coords(
            val lat: Double,
            val lon: Double
        ) {

            fun toDomainModel() = Event.Place.Coords(
                latitude = lat,
                longitude = lon
            )
        }

        fun toDomainModel() = Event.Place(
            id, title, coords.toDomainModel()
        )
    }

    data class Date(
        val start: Long?,
        val end: Long?
    ) {

        fun toDomainModel() = Event.Date(
            start, end
        )
    }

    fun toDomainModel() = Event(
        id = id,
        images = images?.map { it.toDomainModel() },
        title = title,
        fullDescription = body_text,
        place = place?.toDomainModel(),
        dates = dates?.first()?.let {
            if (it.start == null || it.end == null) return@let null
            DateUtil.convertDatesPeriod(it.start, it.end)
        },
        price = if (price.isEmpty()) null else price
    )
}