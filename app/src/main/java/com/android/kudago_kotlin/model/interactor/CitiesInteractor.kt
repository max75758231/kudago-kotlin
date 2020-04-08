package com.android.kudago_kotlin.model.interactor

import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.model.data.repository.CitiesRepository
import javax.inject.Inject

class CitiesInteractor @Inject constructor(
    private val citiesRepository: CitiesRepository
) {

    suspend fun getCities(): List<City> = citiesRepository.getCities().map { city ->
        if (getCity() == city.name) {
            city.copy(isSelected = true)
        }
        return@map city
    }

    fun setCity(cityname: String) = citiesRepository.setCity(cityname)

    fun getCity() = citiesRepository.getCity()
}