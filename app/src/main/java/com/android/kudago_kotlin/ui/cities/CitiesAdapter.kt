package com.android.kudago_kotlin.ui.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.domain.City
import com.android.kudago_kotlin.util.setVisible
import kotlinx.android.synthetic.main.item_city.view.*

class CitiesAdapter(
    val cities: List<City>,
    private val citySelectedListener: OnCitySelectedListener
) : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CitiesViewHolder(itemView, citySelectedListener)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    class CitiesViewHolder(itemView: View, private val citySelectedListener: OnCitySelectedListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: City) {
            itemView.tv_city.text = city.name
            itemView.iv_city_checked.setVisible(city.isSelected)

            itemView.setOnClickListener { citySelectedListener.onCitySelected(city) }
        }
    }
}

interface OnCitySelectedListener {
    fun onCitySelected(city: City)
}