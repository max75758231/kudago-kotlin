package com.android.kudago_kotlin.util

import android.content.Context
import com.android.kudago_kotlin.R

object CitySlugUtil {

    fun getCityBySlug(context: Context, citySlug: String): String {
        return when(citySlug) {
            "spb" -> context.getString(R.string.city_spb)
            "nsk" -> context.getString(R.string.city_nsk)
            "kzn" -> context.getString(R.string.city_kzn)
            "ekb" -> context.getString(R.string.city_ekb)
            "nnv" -> context.getString(R.string.city_nn)
            "krasnoyarsk" -> context.getString(R.string.city_ksn)
            "krd" -> context.getString(R.string.city_krd)
            "sochi" -> context.getString(R.string.city_sochi)
            "online" -> context.getString(R.string.city_online)
            else -> context.getString(R.string.city_msk)
        }
    }
}