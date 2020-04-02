package com.android.kudago_kotlin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.Events
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var eventsViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App().component.inject(this)

        rv_events.adapter = EventsAdapter()
        eventsViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        initObservers()

        swipe_refresh.setOnRefreshListener { swipe_refresh.isRefreshing = false }
    }

    private fun initObservers() {
        eventsViewModel.events.observe(this, Observer {
            showEvents(it)
        })
        eventsViewModel.progress.observe(this, Observer {
            showProgress(it)
        })
    }

    private fun showEvents(events: PagedList<Events.Event>) {
        showProgress(false)
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
