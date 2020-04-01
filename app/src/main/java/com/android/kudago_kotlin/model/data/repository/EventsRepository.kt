package com.android.kudago_kotlin.model.data.repository

import com.android.kudago_kotlin.model.data.server.ApiService
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getEvents(page: String, size: Int) = api.getEvents(size, page).toDomainModel()
}