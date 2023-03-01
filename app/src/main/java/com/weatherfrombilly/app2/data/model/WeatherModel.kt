package com.weatherfrombilly.app2.data.model

data class WeatherModel(
    val temperature: Int,
    val cityName: String,
    val windSpeed: Float,
    val humidity: Int,
    val desc: String
)
