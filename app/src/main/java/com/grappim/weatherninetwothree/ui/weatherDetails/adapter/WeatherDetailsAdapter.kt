package com.grappim.weatherninetwothree.ui.weatherDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.databinding.ItemWeatherBinding
import com.grappim.weatherninetwothree.domain.model.base.TemperatureUnit
import com.grappim.weatherninetwothree.domain.model.weather.DailyWeather

class WeatherDetailsAdapter(private val temperatureUnit: TemperatureUnit) :
    ListAdapter<DailyWeather, WeatherDetailsAdapter.WeatherDetailsViewHolder>(
        WeatherDetailsDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDetailsViewHolder {
        val binding = ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WeatherDetailsViewHolder(
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dailyWeather: DailyWeather) {
            with(binding) {
                tvDate.text = dailyWeather.time
                tvWeatherType.text = dailyWeather.description
                ivWeatherType.load(dailyWeather.icon)

                val temperatureString = when (temperatureUnit) {
                    TemperatureUnit.C -> R.string.temp_day_night_c
                    TemperatureUnit.F -> R.string.temp_day_night_f
                }
                tvTemperature.text = binding.root.context.getString(
                    temperatureString,
                    dailyWeather.dayTemperature,
                    dailyWeather.nightTemperature
                )
            }
        }

    }
}