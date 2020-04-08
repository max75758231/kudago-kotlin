package com.android.kudago_kotlin.ui.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.kudago_kotlin.R
import com.android.kudago_kotlin.model.data.server.entity.City
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

            itemView.setOnClickListener { citySelectedListener.onCitySelected(city) }
        }
    }
}

interface OnCitySelectedListener {
    fun onCitySelected(city: City)
}