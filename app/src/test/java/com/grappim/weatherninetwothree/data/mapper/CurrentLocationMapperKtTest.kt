package com.grappim.weatherninetwothree.data.mapper

import com.grappim.weatherninetwothree.data.model.geocoding.CurrentLocationDTO
import com.grappim.weatherninetwothree.domain.model.location.CurrentLocation
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class CurrentLocationMapperKtTest {
    @Test
    fun `map currentLocation dto to domain`() {
        val expected = CurrentLocation("name", "country")
        val actual = CurrentLocationDTO("name", "country").toDomain()

        expected shouldBeEqualTo actual
    }
}