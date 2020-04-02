package com.android.kudago_kotlin.model.data.server

import com.android.kudago_kotlin.model.data.server.entity.Events
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events/")
    suspend fun getEvents(@Query("fields") fields: String,
                          @Query("page_size") pageSize: Int,
                          @Query("page") page: String,
                          @Query("text_format") textFormat: String,
                          @Query("expand") expandedField: String): Events

    @GET("locations/")
    suspend fun getCities()
}