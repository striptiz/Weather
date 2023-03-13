package com.weatherfrombilly.app2.data.mapper

import com.weatherfrombilly.app2.data.model.IconModel
import com.weatherfrombilly.app2.data.model.WeatherModel
import com.weatherfrombilly.app2.data.model.WeekWeatherModel
import com.weatherfrombilly.app2.data.model.gis.CurrentWeatherWeekResponse
import java.util.*

class WeatherMapper {
    fun map(data: com.weatherfrombilly.app2.data.model.gis.CurrentWeatherResponse): WeatherModel {
        return WeatherModel(
            temperature = data.data.temperature,
            cityName = data.data.cityName,
            windSpeed = data.data.windSpeed.toFloat(),
            humidity = data.data.humidity,
            desc = data.data.desc,
            date = Date()
        )
    }

    fun map(data: CurrentWeatherWeekResponse): List<WeekWeatherModel> {
        return data.data.days.map {
            WeekWeatherModel(
                date = it.date,
                temperature = it.temperature,
                desc = it.desc,
                icon = IconModel("fog")
            )
        }
    }
}
