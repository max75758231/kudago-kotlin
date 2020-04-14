package com.android.kudago_kotlin.model.interactor

import android.content.Context
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.domain.ErrorEntity
import com.android.kudago_kotlin.domain.Result
import com.android.kudago_kotlin.model.data.repository.CitiesRepository
import com.android.kudago_kotlin.util.CitySlugUtil
import javax.inject.Inject

class CitiesInteractor @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val context: Context
) {

    suspend fun getCities(): Result<List<City>> {
        val cities = citiesRepository.getCities()
        if (cities.isNullOrEmpty()) {
            return Result.Error(ErrorEntity.EmptyListError)
        } else {
            return Result.Success(
                cities.map { city ->
                    if (getCity() == city.name) {
                        city.isSelected = true
                    }
                    return@map city
                }
            )
        }
    }

    fun setCity(citySlug: String) = citiesRepository.setCity(citySlug)

    fun getCity() = CitySlugUtil.getCityBySlug(context, citiesRepository.getCity())
}