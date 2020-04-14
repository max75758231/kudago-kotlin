package com.android.kudago_kotlin.model.interactor

import android.content.Context
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.model.data.repository.CitiesRepository
import com.android.kudago_kotlin.util.CitySlugUtil
import javax.inject.Inject

class CitiesInteractor @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val context: Context
) {

    suspend fun getCities(): List<City> = citiesRepository.getCities().map { city ->
        val mappedCity = city.toDomainModel()
        if (getCity() == mappedCity.name) {
            mappedCity.copy(isSelected = true)
        }
        return@map mappedCity
    }

    fun setCity(citySlug: String) = citiesRepository.setCity(citySlug)

    fun getCity() = CitySlugUtil.getCityBySlug(context, citiesRepository.getCity())
}