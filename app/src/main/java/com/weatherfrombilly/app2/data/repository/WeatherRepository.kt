package com.weatherfrombilly.app2.data.repository

import com.weatherfrombilly.app2.data.api.GismeteoApi
import com.weatherfrombilly.app2.data.mapper.WeatherMapper
import com.weatherfrombilly.app2.data.model.WeatherModel
import com.weatherfrombilly.app2.data.model.WeekWeatherModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(val mapper: WeatherMapper = WeatherMapper()) {
    private val gisApi by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.nopbreak.ru/")
            .build()

        retrofit.create(GismeteoApi::class.java)
    }

    fun getWeather(): Single<WeatherModel> {
        return gisApi.getCurrentWeather(11878).subscribeOn(Schedulers.io()).map(mapper::map)
    }

    fun getWeekWeather(): Single<List<WeekWeatherModel>> {
        return gisApi.getWeekWeather(11878).subscribeOn(Schedulers.io()).map(mapper::map)
    }
}