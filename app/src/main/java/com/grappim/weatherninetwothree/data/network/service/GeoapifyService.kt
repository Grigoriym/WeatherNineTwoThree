package com.grappim.weatherninetwothree.data.network.service

import com.grappim.weatherninetwothree.data.model.AutocompleteResponseDTO
import com.grappim.weatherninetwothree.data.network.interceptors.RequestWithGeoApifyApi
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyService {

    @GET("geocode/autocomplete")
    @RequestWithGeoApifyApi
    suspend fun searchLocation(
        @Query("text") searchQuery: String,
    ): AutocompleteResponseDTO

}