package com.weatherfrombilly.app2.data.api

import com.weatherfrombilly.app2.data.model.CurrentWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TomorrowApi {
    @GET("/v4/weather/realtime?location=lugansk&apikey=neMacSvYUeYhbbxY4k73woC5ZLwg8JVW")
    fun getCurrentWeather(): Single<CurrentWeatherResponse>
}
