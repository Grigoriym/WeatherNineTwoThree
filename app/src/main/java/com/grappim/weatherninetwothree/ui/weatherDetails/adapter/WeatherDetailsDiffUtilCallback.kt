package com.grappim.weatherninetwothree.ui.weatherDetails.adapter

import androidx.recyclerview.widget.DiffUtil
import com.grappim.weatherninetwothree.domain.model.weather.DailyWeather

class WeatherDetailsDiffUtilCallback : DiffUtil.ItemCallback<DailyWeather>() {

    override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
        oldItem.weatherCondition == newItem.weatherCondition &&
                oldItem.dayTemperature == newItem.dayTemperature &&
                oldItem.description == newItem.description &&
                oldItem.icon == newItem.icon &&
                oldItem.eveningTemperature == newItem.eveningTemperature
}