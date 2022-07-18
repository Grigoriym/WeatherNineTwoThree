package com.grappim.weatherninetwothree.ui.searchCity.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentLocationInfo(
    val name: String,
    val longitude: Double,
    val latitude: Double
) : Parcelable
