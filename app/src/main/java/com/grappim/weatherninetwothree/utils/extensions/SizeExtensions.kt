package com.grappim.weatherninetwothree.utils.extensions

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

val Number.toPx: Float
    get() = if (this == 0) {
        0f
    } else {
        this.toFloat() * Resources.getSystem().displayMetrics.density
    }

internal val Int.dp: Int
    @JvmSynthetic inline get() = if (this == 0) {
        0
    } else {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }