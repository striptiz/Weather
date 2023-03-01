package com.weatherfrombilly.app2.data.api

import com.weatherfrombilly.app2.data.model.gis.CurrentWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GismeteoApi {
    @GET("/gismeteo/weather")
    fun getCurrentWeather(@Query("city") city: Int): Single<CurrentWeatherResponse>
}