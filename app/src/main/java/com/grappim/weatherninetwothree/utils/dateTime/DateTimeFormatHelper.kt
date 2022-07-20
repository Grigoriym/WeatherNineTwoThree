package com.grappim.weatherninetwothree.utils.dateTime

import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatHelper {

    fun parse(
        time: Long,
        dateTimeFormatter: DateTimeFormatter
    ): String = Instant.ofEpochSecond(time)
        .atZone(ZonedDateTime.now().zone)
        .format(dateTimeFormatter)

}