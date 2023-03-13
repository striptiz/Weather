package com.weatherfrombilly.app2.data.model.gis.week

data class CurrentWeatherWeekDataItem(
    val date: String,
    val temperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val desc: String
)
