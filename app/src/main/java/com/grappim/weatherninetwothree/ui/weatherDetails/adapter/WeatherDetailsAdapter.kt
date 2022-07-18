package com.grappim.weatherninetwothree.ui.weatherDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.databinding.ItemWeatherBinding
import com.grappim.weatherninetwothree.domain.model.weather.DailyWeather

class WeatherDetailsAdapter :
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
                tvTemperature.text = binding.root.context.getString(
                    R.string.temp_day_night,
                    dailyWeather.dayTemperature,
                    dailyWeather.nightTemperature
                )
            }
        }

    }
}