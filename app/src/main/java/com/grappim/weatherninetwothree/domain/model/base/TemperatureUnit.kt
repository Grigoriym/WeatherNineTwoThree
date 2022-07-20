package com.grappim.weatherninetwothree.domain.model.base

enum class TemperatureUnit(val title: String) {
    C("celsius"),
    F("fahrenheit");

    companion object {
        fun getUnit(value: String): TemperatureUnit =
            when (value) {
                C.title -> C
                F.title -> F
                else -> C
            }

        fun getDefault(): TemperatureUnit = C
    }
}