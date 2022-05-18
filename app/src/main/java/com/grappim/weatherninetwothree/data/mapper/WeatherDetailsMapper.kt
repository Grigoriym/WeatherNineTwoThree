package com.grappim.weatherninetwothree.data.mapper

import com.grappim.weatherninetwothree.data.model.weather.WeatherCurrentDailyDTO
import com.grappim.weatherninetwothree.domain.model.weather.CurrentWeather
import com.grappim.weatherninetwothree.domain.model.weather.DailyWeather
import com.grappim.weatherninetwothree.domain.model.weather.WeatherDetails
import com.grappim.weatherninetwothree.utils.date_time.DateTimeFormatHelper
import com.grappim.weatherninetwothree.utils.extensions.getFormattedWithTemperatureSign
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import java.util.*

fun WeatherCurrentDailyDTO.toDomain(
    iconHost: String,
    dfWholeNumber: DecimalFormat,
    dtStandard: DateTimeFormatter
): WeatherDetails {
    val currentIconUrl = "${iconHost}${this.current.weather.first().icon}@2x.png"
    val currentWeatherTime = DateTimeFormatHelper.parse(
        this.current.dt,
        dtStandard
    )
    val currentTemp = dfWholeNumber.format(this.current.temp).getFormattedWithTemperatureSign()
    return WeatherDetails(
        timezone = this.timezone,
        currentWeather = CurrentWeather(
            icon = currentIconUrl,
            description = this.current.weather.first().description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            },
            main = this.current.weather.first().main,
            temp = currentTemp,
            feelsLike = this.current.feelsLike,
            humidity = this.current.humidity,
            windSpeed = this.current.windSpeed,
            weatherCondition = this.current.weather.first().id.toString(),
            currentTime = currentWeatherTime
        ),
        daily = this.daily.drop(1).map { dailyWeather ->
            val dayTemp =
                dfWholeNumber.format(dailyWeather.temp.day).getFormattedWithTemperatureSign()
            val dayNight =
                dfWholeNumber.format(dailyWeather.temp.night).getFormattedWithTemperatureSign()
            val iconUrl = "${iconHost}${dailyWeather.weather.first().icon}@2x.png"
            val dailyWeatherTime = DateTimeFormatHelper.parse(
                dailyWeather.dt,
                dtStandard
            )

            DailyWeather(
                morningTemp = dailyWeather.temp.morn,
                dayTemperature = dayTemp,
                eveningTemperature = dailyWeather.temp.eve,
                nightTemperature = dayNight,
                humidity = dailyWeather.humidity,
                windSpeed = dailyWeather.windSpeed,
                weatherCondition = dailyWeather.weather.first().id.toString(),
                icon = iconUrl,
                description = dailyWeather.weather.first().description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
                main = dailyWeather.weather.first().main,
                time = dailyWeatherTime
            )
        }
    )
}