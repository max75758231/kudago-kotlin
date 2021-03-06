package com.android.kudago_kotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.model.data.repository.EventsRepository
import com.android.kudago_kotlin.model.interactor.CitiesInteractor
import com.android.kudago_kotlin.model.interactor.EventsDataSource
import com.android.kudago_kotlin.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val citiesInteractor: CitiesInteractor
) : BaseViewModel() {

    private var eventsLiveData: LiveData<PagedList<Events.Event>>
    val events: LiveData<PagedList<Events.Event>>
        get() = eventsLiveData

    private var cityLiveData: MutableLiveData<String> = MutableLiveData()
    val city: LiveData<String>
        get() = cityLiveData

    private val progressInitialLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressInitial: LiveData<Boolean>
        get() = progressInitialLiveData

    private val progressPagingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressPaging: LiveData<Boolean>
        get() = progressPagingLiveData

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(EVENTS_PAGE_SIZE)
            .setInitialLoadSizeHint(EVENTS_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        eventsLiveData = getPagedListBuilder(config).build()

        cityLiveData.value = citiesInteractor.getCity()
    }

    private fun getPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, Events.Event> {

        val dataSourceFactory = object : DataSource.Factory<String, Events.Event>() {
            override fun create(): DataSource<String, Events.Event> {
                val dataSource = EventsDataSource(eventsRepository)
                dataSource.coroutineScope = ioScope
                dataSource.onPagingLoadingStarted = { progressPagingLiveData.postValue(true) }
                dataSource.onPagingLoadingFinished = { progressPagingLiveData.postValue(false) }
                dataSource.onInitialLoadingStarted = { progressInitialLiveData.postValue(true) }
                dataSource.onInitialLoadingFinished = { progressInitialLiveData.postValue(false) }
                return dataSource
            }
        }
        return LivePagedListBuilder<String, Events.Event>(dataSourceFactory, config)
    }

    fun updateEventsData() {
        eventsLiveData.value?.dataSource?.invalidate()
    }

    fun updateCity(city: City) {
        citiesInteractor.setCity(city.slug)
    }
}

private const val EVENTS_PAGE_SIZE = 20