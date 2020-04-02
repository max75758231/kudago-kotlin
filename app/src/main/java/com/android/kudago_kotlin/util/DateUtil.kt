package com.android.kudago_kotlin.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun convertDatesPeriod(dateStart: Long, dateEnd: Long): String? {
        val dateStartFormatted = Date(dateStart * 1000L)
        val dateEndFormatted = Date(dateEnd * 1000L)

        val formatDay: DateFormat = SimpleDateFormat("dd")
        val formatMonth: DateFormat = SimpleDateFormat("MMMM")

        // TODO: different cities' timezones handling
        formatDay.timeZone = TimeZone.getTimeZone("GMT+3")
        formatMonth.timeZone = TimeZone.getTimeZone("GMT+3")

        val startDay = formatDay.format(dateStartFormatted)
        val startMonth = formatMonth.format(dateStartFormatted)
        val endDay = formatDay.format(dateEndFormatted)
        val endMonth = formatMonth.format(dateEndFormatted)

        return if (startDay == endDay && startMonth == endMonth) {
            "$startDay $startMonth"
        } else if (startMonth == endMonth) {
            "$startDay - $endDay $startMonth"
        } else {
            "$startDay $startMonth - $endDay $endMonth"
        }
    }
}