package com.grappim.weatherninetwothree.utils.extensions

fun String.getFormattedWithTemperatureSign(): String =
    if (this.asBigDecimal().isLessThanZero()) {
        this
    } else {
        "+${this}"
    }