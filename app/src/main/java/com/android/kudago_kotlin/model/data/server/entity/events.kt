package com.android.kudago_kotlin.model.data.server.entity

import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.util.DateUtil

data class Events(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Event>
) {

    data class Event(
        val id: Long,
        val images: List<Image>?,
        val title: String,
        val description: String,
        val place: Place?,
        val dates: List<Date>?,
        val price: String
    ) {

        data class Image(
            val image: String
        ) {

            fun toDomainModel() = Events.Event.Image(
                image
            )
        }

        data class Place(
            val id: Long,
            val title: String
        ) {

            fun toDomainModel() = Events.Event.Place(
                id, title
            )
        }

        data class Date(
            val start: Long?,
            val end: Long?
        ) {

            fun toDomainModel() = Events.Event.Date(
                start, end
            )
        }

        fun toDomainModel() = Events.Event(
            id = id,
            images = images?.map { it.toDomainModel() },
            title = title,
            description = description,
            place = place?.toDomainModel(),
            dates = dates?.first()?.let {
                if (it.start == null || it.end == null) return@let null
                DateUtil.convertDatesPeriod(it.start, it.end)
            },
            price = if (price.isEmpty()) null else price
        )
    }

    fun toDomainModel() = Events(
        count = count,
        previousPage = previous,
        nextPage = next,
        results = results.map { it.toDomainModel() }
    )
}