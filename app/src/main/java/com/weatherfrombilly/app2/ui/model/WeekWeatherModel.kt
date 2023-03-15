package com.weatherfrombilly.app2.ui.model

import java.util.Date

data class WeekWeatherModel(
    val date: Date,
    val temperature: Int,
    val icon: IconModel,
    val desc: String,
    val windSpeed: Int,
    val humidity: Int
)
