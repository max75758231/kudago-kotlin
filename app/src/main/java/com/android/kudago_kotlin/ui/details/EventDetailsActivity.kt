package com.android.kudago_kotlin.ui.details

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.ui.base.BaseActivity
import com.android.kudago_kotlin.util.setTextOrHideIfNull
import com.android.kudago_kotlin.util.toast
import kotlinx.android.synthetic.main.activity_details.*
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject

class EventDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var eventDetailsViewModel: EventDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        App().component.inject(this)

        val eventId = intent.extras?.getLong("event_id")

        eventDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)[EventDetailsViewModel::class.java]

        eventDetailsViewModel.eventDetails.observe(this, Observer { event ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_descr_full.text = Html.fromHtml(event.fullDescription, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tv_descr_full.text = Html.fromHtml(event.fullDescription)
            }
            tv_descr_full.movementMethod = LinkMovementMethod.getInstance()
            tv_title.text = event.title
            tv_location.setTextOrHideIfNull(event.place?.title)
            tv_date.setTextOrHideIfNull(event.dates)
            tv_price.setTextOrHideIfNull(event.price)

            event.images?.map { it.imageUrl }.let { initImagesViewPager(it!!) }
        })

        eventId?.let {
            eventDetailsViewModel.loadDetails(it)
        } ?: toast("Ошибка получения информации")
    }

    private fun initImagesViewPager(images: List<String>) {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)


        val adapter = ViewPagerAdapter(this, images)
        val indicator = findViewById<CircleIndicator>(R.id.circle_indicator)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
    }
}