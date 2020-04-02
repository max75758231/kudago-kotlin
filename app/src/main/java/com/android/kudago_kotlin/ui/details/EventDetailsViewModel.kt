package com.android.kudago_kotlin.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kudago_kotlin.domain.Event
import com.android.kudago_kotlin.model.data.repository.EventsRepository
import com.android.kudago_kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
) : BaseViewModel() {

    private var eventDetailsLiveData: MutableLiveData<Event> = MutableLiveData()
    val eventDetails: LiveData<Event>
        get() = eventDetailsLiveData

    fun loadDetails(eventId: Long) {
        ioScope.launch {
            eventDetailsLiveData.postValue(eventsRepository.getEventDetails(eventId))
        }
    }
}