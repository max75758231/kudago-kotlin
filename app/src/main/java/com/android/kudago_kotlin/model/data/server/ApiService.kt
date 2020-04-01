package com.android.kudago_kotlin.model.data.server

import com.android.kudago_kotlin.model.data.server.entity.Events
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events/?fields=id,dates,title,place,price,description,images&expand=place")
    suspend fun getEvents(@Query("page_size") pageSize: Int? = 20,
                          @Query("page") page: String): Events

    @GET("locations/")
    suspend fun getCities()
}