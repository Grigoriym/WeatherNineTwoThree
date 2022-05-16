package com.grappim.weatherninetwothree.data.mapper

import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.domain.model.CurrentLocation

fun CurrentLocationDTO.toDomain(): CurrentLocation =
    CurrentLocation(
        name = this.name,
        country = this.country
    )