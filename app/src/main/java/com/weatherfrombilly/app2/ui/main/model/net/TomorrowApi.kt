package com.weatherfrombilly.app2.ui.main.model.net

import com.weatherfrombilly.app2.ui.main.model.net.model.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface TomorrowApi {
    @GET("/v4/weather/realtime?location=lugansk&apikey=neMacSvYUeYhbbxY4k73woC5ZLwg8JVW")
    fun getCurrentWeather(): Call<CurrentWeatherResponse>
}