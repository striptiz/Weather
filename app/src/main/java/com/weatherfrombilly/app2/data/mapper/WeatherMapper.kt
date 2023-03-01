package com.weatherfrombilly.app2.data.mapper

import com.weatherfrombilly.app2.data.model.CurrentWeatherResponse
import com.weatherfrombilly.app2.data.model.WeatherModel

class WeatherMapper {
    fun map(data: CurrentWeatherResponse): WeatherModel {
        return WeatherModel(
            temperature = data.data.values.temperature.toInt(),
            cityName = data.location.name,
            windSpeed = data.data.values.windSpeed,
            humidity = 60,
            desc = ""
        )
    }

    fun map(data: com.weatherfrombilly.app2.data.model.gis.CurrentWeatherResponse): WeatherModel {
        return WeatherModel(
            temperature = data.data.temperature,
            cityName = data.data.cityName,
            windSpeed = data.data.windSpeed.toFloat(),
            humidity = data.data.humidity,
            desc = data.data.desc
        )
    }
}