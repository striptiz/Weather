package com.weatherfrombilly.app2.data.model.gis.current

data class CurrentWeatherData(
    val cityName: String,
    val temperature: Int,
    val temperature_min: Int,
    val temperature_max: Int,
    val windSpeed: Int,
    val humidity: Int,
    val desc: String
)
