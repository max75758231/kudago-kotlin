package com.android.kudago_kotlin.util

import com.android.kudago_kotlin.model.data.server.entity.Events
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun handleEventDates(dates: List<Events.Event.Date>?): String? {
        if (dates == null) return null

        val datesStringBuilder = StringBuilder()
        dates.filter { it.start != null && it.end != null }
            .forEachIndexed { index, date ->
                datesStringBuilder.append(convertDatesPeriod(date.start!!, date.end!!))
                if (!dates.isLast(index)) datesStringBuilder.append(", ")
            }

        return datesStringBuilder.toString()
    }

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