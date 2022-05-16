package com.grappim.weatherninetwothree.utils.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun String?.asBigDecimal(scale: Int = 3): BigDecimal =
    if (this.isNullOrBlank()) {
        BigDecimal("0").setScale(scale, RoundingMode.HALF_EVEN)
    } else {
        BigDecimal(this).setScale(scale, RoundingMode.HALF_EVEN)
    }

fun String?.asBigDecimalOrNull(scale: Int = 3): BigDecimal? =
    try {
        this?.asBigDecimal(scale)
    } catch (e: Exception) {
        null
    }

fun BigDecimal.isNotEqualsZero(): Boolean = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.isLessThan(value: BigDecimal): Boolean =
    this.compareTo(value) == -1

fun BigDecimal.isLessThanZero(): Boolean =
    this.compareTo(BigDecimal.ZERO) == -1

fun BigDecimal.isLessThanOrEquals(value: BigDecimal): Boolean =
    this.compareTo(value) == -1 ||
            this.compareTo(value) == 0