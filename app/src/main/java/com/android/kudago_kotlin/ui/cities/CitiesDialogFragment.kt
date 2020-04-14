package com.android.kudago_kotlin.ui.cities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.kudago_kotlin.App
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.domain.Result
import com.android.kudago_kotlin.util.setVisible
import com.android.kudago_kotlin.util.toast
import kotlinx.android.synthetic.main.dialog_cities.*
import javax.inject.Inject

class CitiesDialogFragment(val onCitySelectionResultListener: OnCitySelectionResultListener) : AppCompatDialogFragment(), OnCitySelectedListener {

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
            citiesViewModel.cities.observe(this, Observer { result ->

                when(result) {
                    is Result.Success -> {
                        showProgress(false)
                        dialog?.rv_cities?.adapter = CitiesAdapter(result.data!!, this)
                    }
                    is Result.Loading -> showProgress(true)
                    is Result.Error -> {
                        showProgress(false)
                        activity.toast("no data")
                    }
                }
            })

            citiesViewModel.citiesSearch.observe(this, Observer { result ->
                when(result) {
                    is Result.Success -> {
                        showProgress(false)
                        dialog?.rv_cities?.adapter = CitiesAdapter(result.data!!, this)
                    }
                    is Result.Loading -> showProgress(true)
                    is Result.Error -> {
                        showProgress(false)
                        activity.toast("no data")
                    }
                }
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

            it.et_cities_search.addTextChangedListener { citiesViewModel.searchCities(it.toString()) }
        }

    }

    private fun showProgress(isVisible: Boolean) {
        dialog?.let { view ->
            view.pb_cities.setVisible(isVisible)
            view.rv_cities.setVisible(!isVisible)
        }
    }

    override fun onCitySelected(city: City) {
        onCitySelectionResultListener.onCitySelectedSuccess(city)
        dismiss()
    }
}

interface OnCitySelectionResultListener {
    fun onCitySelectedSuccess(city: City)
}