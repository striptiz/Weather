package com.weatherfrombilly.app2.ui.model

import java.util.Date

data class WeatherModel(
    val temperature: Int,
    val temperature_min: Int,
    val temperature_max: Int,
    val cityName: String,
    val windSpeed: Float,
    val humidity: Int,
    val desc: String,
    val date: Date,
    val iconModel: IconModel
)
