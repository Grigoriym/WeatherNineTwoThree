package com.grappim.weatherninetwothree.ui.search_city

import androidx.recyclerview.widget.DiffUtil
import com.grappim.weatherninetwothree.domain.FoundLocation

class CitiesDiffUtilCallback : DiffUtil.ItemCallback<FoundLocation>() {
    override fun areItemsTheSame(oldItem: FoundLocation, newItem: FoundLocation): Boolean =
        oldItem.cityName == newItem.cityName &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude

    override fun areContentsTheSame(oldItem: FoundLocation, newItem: FoundLocation): Boolean =
        oldItem == newItem
}