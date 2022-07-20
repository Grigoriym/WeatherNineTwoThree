package com.grappim.weatherninetwothree.domain.model.base

enum class Units(val title: String) {
    METRIC("metric"),
    IMPERIAL("imperial");

    companion object {
        fun getUnit(value: String): Units =
            when (value) {
                METRIC.title -> METRIC
                IMPERIAL.title -> IMPERIAL
                else -> METRIC
            }

        fun getDefault(): Units = METRIC


    }
}