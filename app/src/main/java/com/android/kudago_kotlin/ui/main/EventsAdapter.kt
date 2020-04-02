package com.android.kudago_kotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.Events
import com.android.kudago_kotlin.util.setTextOrHideIfNull
import com.android.kudago_kotlin.util.setVisible
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.*
import kotlinx.android.synthetic.main.item_event.view.*

class EventsAdapter : PagedListAdapter<Events.Event, RecyclerView.ViewHolder>(EventsDiffUtil()) {

    private var currentState: State = State.OK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_event -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
                EventsViewHolder(view)
            }
            R.layout.item_progress -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
                ProgressViewHolder(view)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_event -> getItem(position)?.let { (holder as EventsViewHolder).onBind(it) }
            R.layout.item_progress -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && currentState == State.LOADING)
            R.layout.item_progress
        else
            R.layout.item_event
    }

    override fun getItemCount() = super.getItemCount() + countExtraRows()

    private fun countExtraRows() =
        when (currentState) {
            State.LOADING -> 1
            State.OK -> 0
        }

    fun setState(state: State) {
        if (currentState != state) {
            currentState = state
            when (state) {
                State.LOADING -> notifyItemInserted(super.getItemCount())
                State.OK -> notifyItemRemoved(super.getItemCount())
            }
        }
    }

    inner class EventsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun onBind(event: Events.Event) {
            with(containerView) {
                tv_title.text = event.title
                tv_details.text = event.description
                tv_location.setTextOrHideIfNull(event.place?.title)
                tv_date.setTextOrHideIfNull(event.dates)
                tv_price.setTextOrHideIfNull(event.price)
                event.images?.let {
                    Glide.with(iv_image)
                        .load(it[0].imageUrl)
                        .into(iv_image)
                }
            }
        }
    }

    inner class ProgressViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    class EventsDiffUtil : DiffUtil.ItemCallback<Events.Event>() {

        override fun areItemsTheSame(oldItem: Events.Event, newItem: Events.Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Events.Event, newItem: Events.Event): Boolean {
            return oldItem == newItem
        }
    }

    enum class State {
        LOADING,
        OK
    }
}