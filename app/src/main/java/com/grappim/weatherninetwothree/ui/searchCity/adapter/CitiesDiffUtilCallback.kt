package com.grappim.weatherninetwothree.ui.searchCity.adapter

import androidx.recyclerview.widget.DiffUtil
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation

class CitiesDiffUtilCallback : DiffUtil.ItemCallback<FoundLocation>() {
    override fun areItemsTheSame(oldItem: FoundLocation, newItem: FoundLocation): Boolean =
        oldItem.cityName == newItem.cityName &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude

    override fun areContentsTheSame(oldItem: FoundLocation, newItem: FoundLocation): Boolean =
        oldItem == newItem
}