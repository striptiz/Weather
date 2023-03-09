package com.weatherfrombilly.app2.data.model

import java.util.Date

data class WeekWeatherModel(
    val date: Date,
    val temperature: Int,
    val icon: IconModel,
    val desc: String,
    val iconId: String
)
