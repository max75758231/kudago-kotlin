package com.android.kudago_kotlin.model.interactor

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.model.data.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Provide schools to paging library.
 */
class EventsDataSource(private val eventsRepository: EventsRepository) : PageKeyedDataSource<String, Events.Event>() {

    /**
     * Scope of DataSource running
     */
    var coroutineScope: CoroutineScope? = null
    /**
     * Callback when loading is started
     */
    var onLoadingStarted: OnLoadingStarted? = null
    /**
     * Callback when loading is finished
     */
    var onLoadingFinished: OnLoadingFinished? = null

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Events.Event>) {
        coroutineScope?.launch {
            try {
                onLoadingStarted?.invoke()
                val events = eventsRepository.getEvents("1", params.requestedLoadSize)
                val nextPageLink = events.nextPage
                val next = nextPageLink?.substringAfterLast("page=")?.substringBefore("&")
                callback.onResult(events.results, null, next.toString())
            } catch (exception: Exception) {
                Log.e("myLog", exception.message)
            } finally {
                onLoadingFinished?.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Events.Event>) {}

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Events.Event>) {
        coroutineScope?.launch {
            try {
                onLoadingStarted?.invoke()
                val events = eventsRepository.getEvents(params.key, params.requestedLoadSize)
                val nextPageLink = events.nextPage
                val next = nextPageLink?.substringAfterLast("page=")?.substringBefore("&")
                callback.onResult(events.results, next.toString())
            } catch (exception: Exception) {
                Log.e("myLog", exception.message)
            } finally {
                onLoadingFinished?.invoke()
            }
        }
    }
}

internal typealias OnLoadingStarted = () -> Unit
internal typealias OnLoadingFinished = () -> Unit
