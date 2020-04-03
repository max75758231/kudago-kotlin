package com.android.kudago_kotlin.util

object CitySlugUtil {

    fun getSlugByCity(city: String): String {
        return when(city) {
            "Санкт-Петербург" -> "spb"
            "Новосибирск" -> "nsk"
            "Казань" -> "kzn"
            "Екатеринбург" -> "ekb"
            "Нижний Новгород" -> "nnv"
            "Красноярск" -> "krasnoyarsk"
            "Краснодар" -> "krd"
            "Сочи" -> "sochi"
            "Онлайн" -> "online"
            else -> "msk"
        }
    }
}