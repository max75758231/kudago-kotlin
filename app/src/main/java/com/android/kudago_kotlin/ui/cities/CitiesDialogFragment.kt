package com.android.kudago_kotlin.ui.cities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.util.setVisible
import kotlinx.android.synthetic.main.dialog_cities.*
import javax.inject.Inject

class CitiesDialogFragment(val onCitySelectionResultListener: OnCitySelectionResultListener) : AppCompatDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var citiesViewModel: CitiesViewModel

    private var dialog: AlertDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        App.component.inject(this)

        activity?.let { activity ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CitiesDialog)
            builder.setView(activity.layoutInflater.inflate(R.layout.dialog_cities, null))

            citiesViewModel = ViewModelProviders.of(this, viewModelFactory)[CitiesViewModel::class.java]
            citiesViewModel.cities.observe(this, Observer {
                showProgress(false)
                dialog?.rv_cities?.adapter = CitiesAdapter(it, object : OnCitySelectedListener {
                    override fun onCitySelected(city: City) {
                        onCitySelectionResultListener.onCitySelectedSuccess(city)
                        dismiss()
                    }
                })
            })

            citiesViewModel.loadCities()

            dialog = builder.create()
        }

        return dialog as AlertDialog
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            it.btn_back.setOnClickListener { dismiss() }
        }

    }

    private fun showProgress(isVisible: Boolean) {
        dialog?.let { view ->
            view.pb_cities.setVisible(isVisible)
            view.rv_cities.setVisible(!isVisible)
        }
    }
}

interface OnCitySelectionResultListener {
    fun onCitySelectedSuccess(city: City)
}