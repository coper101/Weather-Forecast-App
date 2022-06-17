package com.darealreally.weatherforecastapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? =
    SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        .parse(this)

fun Double.toCelsius(): Double =
    String.format("%.0f", (this - 273.15)).toDouble()

fun Int.toDayOfWeek() =
    listOf(
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thurs",
        "Fri",
        "Sat"
    )[this]

fun Int.toMonthOfYear() =
    listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )[this]