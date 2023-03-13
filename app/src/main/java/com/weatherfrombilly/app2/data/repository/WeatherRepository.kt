package com.weatherfrombilly.app2.data.repository

import com.weatherfrombilly.app2.data.source.GismeteoSourceData
import com.weatherfrombilly.app2.ui.main.model.LocationModel
import com.weatherfrombilly.app2.ui.main.model.WeatherModel
import com.weatherfrombilly.app2.ui.main.model.WeekWeatherModel
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class WeatherRepository(private val gisSource: GismeteoSourceData) {
    fun getWeather(cityId: Int): Single<WeatherModel> {
        return gisSource.getWeather(cityId).delay(2, TimeUnit.SECONDS)
    }

    fun getWeekWeather(cityId: Int): Single<List<WeekWeatherModel>> {
        return gisSource.getWeekWeather(cityId)
    }

    fun getLocation(city: String): Single<List<LocationModel>> {
        return gisSource.getLocation(city)
    }
}