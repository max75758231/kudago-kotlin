package com.android.kudago_kotlin.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kudago_kotlin.model.data.server.entity.City
import com.android.kudago_kotlin.model.interactor.CitiesInteractor
import com.android.kudago_kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    val citiesInteractor: CitiesInteractor
) : BaseViewModel() {

    private var citiesLiveData: MutableLiveData<List<City>> = MutableLiveData()
    val cities: LiveData<List<City>>
        get() = citiesLiveData

    fun loadCities() {
        ioScope.launch {
            citiesLiveData.postValue(citiesInteractor.getCities())
        }
    }
}