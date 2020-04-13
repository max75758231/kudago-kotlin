package com.android.kudago_kotlin.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.ui.base.BaseActivity
import com.android.kudago_kotlin.util.setLinkableHtmlText
import com.android.kudago_kotlin.util.setTextOrHideIfNull
import com.android.kudago_kotlin.util.setVisible
import com.android.kudago_kotlin.util.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.toolbar_details.*
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject

class EventDetailsActivity : BaseActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var eventDetailsViewModel: EventDetailsViewModel

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        App.component.inject(this)

        val eventId = intent.extras?.getLong("event_id")

        eventDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)[EventDetailsViewModel::class.java]

        eventDetailsViewModel.eventDetails.observe(this, Observer { event ->
            showProgress(false)
            tv_descr_full.setLinkableHtmlText(event.fullDescription)
            tv_descr_full.movementMethod = LinkMovementMethod.getInstance()
            tv_title.text = event.title
            tv_location.setTextOrHideIfNull(event.place?.title)
            tv_date.setTextOrHideIfNull(event.dates)
            tv_price.setTextOrHideIfNull(event.price)

            event.images?.map { it.imageUrl }.let { initImagesViewPager(it!!) }

            event.place?.let {
                initMap(it.coords.latitude, it.coords.longitude)
            } ?: hideMapView()
        })

        eventId?.let {
            eventDetailsViewModel.loadDetails(it)
        } ?: toast("Ошибка получения информации")

        btn_back_details.setOnClickListener { onBackPressed() }

        btn_map_navigate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getString(R.string.details_map_navigation_link, latitude.toString(), longitude.toString()))
            startActivity(intent)
        }
    }

    private fun initImagesViewPager(images: List<String>) {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)


        val adapter = ViewPagerAdapter(this, images)
        val indicator = findViewById<CircleIndicator>(R.id.circle_indicator)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
    }

    private fun initMap(latitude: Double, longitude: Double) {
        map_view.onCreate(null)
        map_view.onResume()
        map_view.getMapAsync(this)

        this.latitude = latitude
        this.longitude = longitude
    }

    private fun showProgress(isVisible: Boolean) {
        pb_details.setVisible(isVisible)
        frame_layout_details.setVisible(!isVisible)
    }

    private fun hideMapView() {
        map_view.setVisible(false)
        btn_map_navigate.setVisible(false)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(this)

        googleMap?.let { googleMap ->
            googleMap.addMarker(
                MarkerOptions()
                    .icon(bitmapFromVector(this, R.drawable.ic_map_marker))
                    .position(LatLng(latitude, longitude))
            )

            googleMap.moveCamera(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition.builder()
                            .target(LatLng(latitude, longitude))
                            .zoom(16f)
                            .build()
                    )
            )

            googleMap.uiSettings.isScrollGesturesEnabled = false
            googleMap.uiSettings.isZoomGesturesEnabled = false
        }
    }

    private fun bitmapFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            MAP_MARKER_HEIGHT,
            MAP_MARKER_WIDTH,
            vectorDrawable.intrinsicWidth + MAP_MARKER_HEIGHT,
            vectorDrawable.intrinsicHeight + MAP_MARKER_WIDTH
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth + MAP_MARKER_HEIGHT,
            vectorDrawable.intrinsicHeight + MAP_MARKER_WIDTH,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

private const val MAP_MARKER_HEIGHT = 39
private const val MAP_MARKER_WIDTH = 30