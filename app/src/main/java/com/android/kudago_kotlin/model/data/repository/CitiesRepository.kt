package com.android.kudago_kotlin.model.data.repository

import com.android.kudago_kotlin.db.DatabaseService
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.model.data.server.ApiService
import com.android.kudago_kotlin.model.data.storage.Prefs
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val api: ApiService,
    private val prefs: Prefs,
    private val databaseService: DatabaseService
) {

    suspend fun getCities(): List<City> {
        val citiesFromDb = getCitiesFromDb()
        if (citiesFromDb.isNullOrEmpty()) {
            return getCitiesFromNetwork()
        } else {
            return citiesFromDb
        }
    }

    private fun getCitiesFromDb() = databaseService.getItemList(City::class.java)

    private suspend fun getCitiesFromNetwork(): List<City> {
        val cities = api.getCities(OrderBy.NAME.name.toLowerCase()).map { it.toDomainModel() }
        saveCitiesToDb(cities)
        return cities
    }

    private fun saveCitiesToDb(cities: List<City>) {
        databaseService.deleteItemList(City::class.java)
        databaseService.saveItemList(cities)
    }

    fun getCity() = prefs.searchCitySlug

    fun setCity(citySlug: String) {
        prefs.searchCitySlug = citySlug
    }

    enum class OrderBy {
        NAME, SLUG, TIMEZONE, COORDS, LANGUAGE, CURRENCY
    }
}