package com.android.kudago_kotlin.model.data.repository

import com.android.kudago_kotlin.model.data.server.ApiService
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getEvents(page: String, size: Int) =
        api.getEvents(FIELDS_TO_RETRIEVE,
                      size,
                      page,
                      TextFormat.PLAIN.toString().toLowerCase(),
                      EXPANDED_FIELDS
        ).toDomainModel()

    enum class TextFormat {
        HTML, PLAIN, TEXT
    }
}

private const val FIELDS_TO_RETRIEVE = "id,dates,title,place,price,description,images"
private const val EXPANDED_FIELDS = "place"