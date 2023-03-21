package com.weatherfrombilly.app2.data.mapper

import com.weatherfrombilly.app2.data.model.gis.current.CurrentWeatherResponse
import com.weatherfrombilly.app2.data.model.gis.location.CurrentLocationResponse
import com.weatherfrombilly.app2.data.model.gis.week.CurrentWeatherWeekResponse
import com.weatherfrombilly.app2.provider.IconResolver
import com.weatherfrombilly.app2.ui.model.LocationModel
import com.weatherfrombilly.app2.ui.model.WeatherModel
import com.weatherfrombilly.app2.ui.model.WeekWeatherModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherMapper(private val iconResolver: IconResolver = IconResolver()) {
    fun map(data: CurrentWeatherResponse): WeatherModel {
        return WeatherModel(
            temperature = data.data.temperature,
            cityName = data.data.cityName,
            windSpeed = data.data.windSpeed.toFloat(),
            humidity = data.data.humidity,
            desc = data.data.desc,
            date = Date(), // FIXME: current date
            iconModel = iconResolver.getIconModel(data.data.desc),
            temperature_max = data.data.temperature_max,
            temperature_min = data.data.temperature_min
        )
    }

    fun map(data: CurrentWeatherWeekResponse): List<WeekWeatherModel> {
        return data.data.days.map {
            WeekWeatherModel(
                date = parse(it.date) ?: Date(),
                temperature = it.temperature,
                desc = it.desc,
                icon = iconResolver.getIconModel(it.desc),
                windSpeed = it.windSpeed,
                humidity = it.humidity,
                tempMax = it.temperature_max,
                tempMin = it.temperature_min
            )
        }
    }

    fun map(data: CurrentLocationResponse): List<LocationModel> {
        return data.data.locations.map {
            LocationModel(it.city_name, it.full_name, it.id)
        }
    }

    companion object {
        private val dateParser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

        fun parse(date: String): Date? {
            return dateParser.parse(date)
        }
    }
}
