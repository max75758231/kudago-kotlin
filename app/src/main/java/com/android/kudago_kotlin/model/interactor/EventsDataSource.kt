package com.android.kudago_kotlin.model.interactor

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.model.data.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Provide schools to paging library.
 */
class EventsDataSource(private val eventsRepository: EventsRepository) : PageKeyedDataSource<String, Events.Event>() {


    var coroutineScope: CoroutineScope? = null
    var onInitialLoadingStarted: OnInitialLoadingStarted? = null
    var onInitialLoadingFinished: OnInitialLoadingFinished? = null
    var onPagingLoadingStarted: OnPagingLoadingStarted? = null
    var onPagingLoadingFinished: OnPagingLoadingFinished? = null

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Events.Event>) {
        coroutineScope?.launch {
            try {
                onInitialLoadingStarted?.invoke()
                val events = eventsRepository.getEvents("1", params.requestedLoadSize)
                val nextPageLink = events.nextPage
                val next = nextPageLink?.substringAfterLast("page=")?.substringBefore("&")
                callback.onResult(events.results, null, next.toString())
            } catch (exception: Exception) {
                Log.e("myLog", exception.message)
            } finally {
                onInitialLoadingFinished?.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Events.Event>) {}

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Events.Event>) {
        coroutineScope?.launch {
            try {
                onPagingLoadingStarted?.invoke()
                val events = eventsRepository.getEvents(params.key, params.requestedLoadSize)
                val nextPageLink = events.nextPage
                val next = nextPageLink?.substringAfterLast("page=")?.substringBefore("&")
                callback.onResult(events.results, next.toString())
            } catch (exception: Exception) {
                Log.e("myLog", exception.message)
            } finally {
                onPagingLoadingFinished?.invoke()
            }
        }
    }
}

internal typealias OnPagingLoadingStarted = () -> Unit
internal typealias OnPagingLoadingFinished = () -> Unit
internal typealias OnInitialLoadingStarted = () -> Unit
internal typealias OnInitialLoadingFinished = () -> Unit
