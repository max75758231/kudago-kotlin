package com.android.kudago_kotlin.model.data.repository

import com.android.kudago_kotlin.model.data.server.ApiService
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getEvents(page: String, size: Int) =
        api.getEvents(LIST_FIELDS_TO_RETRIEVE,
                      size,
                      page,
                      TextFormat.TEXT.toString().toLowerCase(),
                      EXPANDED_FIELDS
        ).toDomainModel()

    suspend fun getEventDetails(eventId: Long) =
        api.getEventDetails(eventId,
                            CONTENT_LANGUAGE_RU,
                            DETAILS_FIELDS_TO_RETRIEVE,
                            EXPANDED_FIELDS,
                            TextFormat.HTML.toString().toLowerCase()
        ).toDomainModel()

    enum class TextFormat {
        HTML, PLAIN, TEXT
    }
}

private const val LIST_FIELDS_TO_RETRIEVE = "id,dates,title,place,price,description,images"
private const val DETAILS_FIELDS_TO_RETRIEVE = "id,dates,title,place,price,body_text,images"
private const val EXPANDED_FIELDS = "place"
private const val CONTENT_LANGUAGE_RU = "ru"
private const val CONTENT_LANGUAGE_EN = "en"