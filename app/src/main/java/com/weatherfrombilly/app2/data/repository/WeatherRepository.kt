package com.weatherfrombilly.app2.data.repository

import com.weatherfrombilly.app2.data.api.TomorrowApi
import com.weatherfrombilly.app2.data.model.CurrentWeatherResponse
import com.weatherfrombilly.app2.data.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    interface ResultCallback {
        fun onResult(data: WeatherModel)
        fun onFailure(msg: String)
    }

    private val tomorrowApi by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tomorrow.io/")
            .build()

        retrofit.create(TomorrowApi::class.java)
    }

    fun getWeatherData(city: String, callback: ResultCallback) {
        tomorrowApi.getCurrentWeather().enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                response.body()?.data?.values?.let { data ->
                    response.body()?.location?.let { loc ->
                        callback.onResult(WeatherModel(data.temperature, loc.name, data.windSpeed))
                        return
                    }
                }

                callback.onFailure("debug1")
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                callback.onFailure(t.toString())
            }
        })
    }
}