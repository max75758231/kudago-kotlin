package com.android.kudago_kotlin.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.domain.ErrorEntity
import com.android.kudago_kotlin.domain.Result
import com.android.kudago_kotlin.model.interactor.CitiesInteractor
import com.android.kudago_kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesInteractor: CitiesInteractor
) : BaseViewModel() {

    private var citiesLiveData: MutableLiveData<Result<List<City>>> = MutableLiveData()
    val cities: LiveData<Result<List<City>>>
        get() = citiesLiveData

    private var citiesSearchLiveData: MutableLiveData<Result<List<City>>> = MutableLiveData()
    val citiesSearch: LiveData<Result<List<City>>>
        get() = citiesSearchLiveData

    fun loadCities() {
        ioScope.launch {
            citiesLiveData.postValue(citiesInteractor.getCities())
        }
    }

    fun searchCities(cityname: String) {
        citiesSearchLiveData.postValue(citiesLiveData.value?.data?.let { data ->
            Result.Success(data.filter {
                it.name.toLowerCase().contains(cityname)
            })
        } ?: Result.Error(ErrorEntity.EmptyListError))
    }
}