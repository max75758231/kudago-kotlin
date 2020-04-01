package com.android.kudago_kotlin.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.model.data.repository.EventsRepository
import com.android.kudago_kotlin.model.interactor.EventsDataSource
import com.android.kudago_kotlin.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
) : BaseViewModel() {

    var eventsLiveData: LiveData<PagedList<Events.Event>>
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(false)
            .build()

        eventsLiveData = initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, Events.Event> {

        val dataSourceFactory = object : DataSource.Factory<String, Events.Event>() {
            override fun create(): DataSource<String, Events.Event> {
                Log.d("myLog", "called ")
                val dataSource = EventsDataSource(eventsRepository)
                dataSource.coroutineScope = ioScope
                dataSource.onLoadingFinished = { progressLiveData.postValue(false) }
                dataSource.onLoadingStarted = { progressLiveData.postValue(true) }
                return dataSource
            }
        }
        return LivePagedListBuilder<String, Events.Event>(dataSourceFactory, config)
    }
}