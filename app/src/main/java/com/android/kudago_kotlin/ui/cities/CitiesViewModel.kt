package com.android.kudago_kotlin.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.model.interactor.CitiesInteractor
import com.android.kudago_kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesInteractor: CitiesInteractor
) : BaseViewModel() {

    private var citiesLiveData: MutableLiveData<List<City>> = MutableLiveData()
    val cities: LiveData<List<City>>
        get() = citiesLiveData

    private var citiesSearchLiveData: MutableLiveData<List<City>> = MutableLiveData()
    val citiesSearch: LiveData<List<City>>
        get() = citiesSearchLiveData

    fun loadCities() {
        ioScope.launch {
            citiesLiveData.postValue(citiesInteractor.getCities())
        }
    }

    fun searchCities(cityname: String) {
        citiesSearchLiveData.postValue(citiesLiveData.value?.filter {
            it.name.toLowerCase().contains(cityname)
        })
    }
}