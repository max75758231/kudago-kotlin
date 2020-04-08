package com.android.kudago_kotlin.model.interactor

import com.android.kudago_kotlin.model.data.repository.CitiesRepository
import javax.inject.Inject

class CitiesInteractor @Inject constructor(val citiesRepository: CitiesRepository) {

    suspend fun getCities() = citiesRepository.getCities()

    fun setCity(cityname: String) = citiesRepository.setCity(cityname)
}