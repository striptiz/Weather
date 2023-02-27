package com.weatherfrombilly.app2.data.model

data class WeatherData(
    val windSpeed: Float = 0f,
    val windDirection: Float = 0f,
    val temperature: Float = 0f,
    val snowIntensity: Float = 0f,
    val sleetIntensity: Float = 0f,
    val rainIntensity: Float = 0f,
    val freezingRainIntensity: Float = 0f,
    val cloudCover: Float = 0f,
)
