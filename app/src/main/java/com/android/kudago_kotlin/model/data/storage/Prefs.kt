package com.android.kudago_kotlin.model.data.storage

import android.content.Context
import com.android.kudago_kotlin.R
import javax.inject.Inject

class Prefs @Inject constructor(
    private val context: Context
) {

    private val appPrefs by lazy { getSharedPreferences(APP_DATA) }

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    var searchCitySlug: String
        get() = appPrefs.getString(KEY_SEARCH_CITY, context.getString(R.string.main_city_default_slug)) ?: context.getString(R.string.main_city_default_slug)
        set(value) = appPrefs.edit().putString(KEY_SEARCH_CITY, value).apply()
}

private const val APP_DATA = "app_data"
private const val KEY_SEARCH_CITY = "search_city"