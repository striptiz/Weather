package com.weatherfrombilly.app2.ui.main.model

import java.util.Date

data class WeekWeatherModel(
    val date: Date,
    val temperature: Int,
    val icon: IconModel,
    val desc: String
)
