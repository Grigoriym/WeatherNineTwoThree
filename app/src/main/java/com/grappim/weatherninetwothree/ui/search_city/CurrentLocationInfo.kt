package com.grappim.weatherninetwothree.ui.search_city

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentLocationInfo(
    val name: String,
    val longitude: Double,
    val latitude: Double
) : Parcelable
