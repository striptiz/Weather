package com.weatherfrombilly.app2.data.model

data class CurrentWeatherResponse(
    val data: CurrentWeatherResponseData,
    val location: CurrentWeatherLocation
)
