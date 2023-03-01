package com.weatherfrombilly.app2.data.model.gis

import com.weatherfrombilly.app2.data.model.CurrentWeatherResponseData

data class CurrentWeatherResponse(
    val errorCode: Int,
    val data: CurrentWeatherData
)
