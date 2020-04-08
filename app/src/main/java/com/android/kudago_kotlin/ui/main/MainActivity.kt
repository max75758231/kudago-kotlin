package com.android.kudago_kotlin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.ui.base.BaseActivity
import com.android.kudago_kotlin.ui.cities.CitiesDialogFragment
import com.android.kudago_kotlin.ui.cities.OnCitySelectionResultListener
import com.android.kudago_kotlin.ui.details.EventDetailsActivity
import com.android.kudago_kotlin.util.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var eventsViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        rv_events.adapter = EventsAdapter { eventId -> onEventClicked(eventId) }
        eventsViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        initObservers()

        swipe_refresh.setOnRefreshListener {
            eventsViewModel.events.value?.dataSource?.invalidate()
        }

        tv_toolbar_city.setOnClickListener {
            val citiesDialog = CitiesDialogFragment(object : OnCitySelectionResultListener {
                override fun onCitySelectedSuccess(city: City) {
                    eventsViewModel.updateCity(city)
                    tv_toolbar_city.text = city.name
                    eventsViewModel.updateData()
                }
            })
            citiesDialog.show(supportFragmentManager, CitiesDialogFragment::class.java.name)
        }
    }

    private fun initObservers() {
        eventsViewModel.events.observe(this, Observer {
            if (swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
            showEvents(it)
        })
        eventsViewModel.progressInitial.observe(this, Observer { visible ->
            rv_events.setVisible(!visible)
            pb_main.setVisible(visible)
        })
        eventsViewModel.progressPaging.observe(this, Observer {
            showProgress(it)
        })
        eventsViewModel.city.observe(this, Observer {
            tv_toolbar_city.text = it
        })
    }

    private fun onEventClicked(eventId: Long) {
        val intent = Intent(this, EventDetailsActivity::class.java)
        intent.putExtra("event_id", eventId)
        startActivity(intent)
    }

    private fun showEvents(events: PagedList<Events.Event>) {
        (rv_events.adapter as EventsAdapter).submitList(events)
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            (rv_events.adapter as EventsAdapter).setState(EventsAdapter.State.LOADING)
        } else {
            (rv_events.adapter as EventsAdapter).setState(EventsAdapter.State.OK)
        }
    }
}
