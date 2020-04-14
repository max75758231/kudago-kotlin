package com.android.kudago_kotlin.model.data.repository

import com.android.kudago_kotlin.model.data.server.ApiService
import com.android.kudago_kotlin.model.data.storage.Prefs
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val api: ApiService,
    private val prefs: Prefs
) {

    suspend fun getCities() = api.getCities(OrderBy.NAME.name.toLowerCase())

    fun getCity() = prefs.searchCitySlug

    fun setCity(citySlug: String) {
        prefs.searchCitySlug = citySlug
    }

    enum class OrderBy {
        NAME, SLUG, TIMEZONE, COORDS, LANGUAGE, CURRENCY
    }
}