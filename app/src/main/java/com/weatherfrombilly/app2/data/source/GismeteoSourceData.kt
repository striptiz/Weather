package com.weatherfrombilly.app2.data.source

import com.weatherfrombilly.app2.data.api.GismeteoApi
import com.weatherfrombilly.app2.data.mapper.WeatherMapper
import com.weatherfrombilly.app2.ui.model.LocationModel
import com.weatherfrombilly.app2.ui.model.WeatherModel
import com.weatherfrombilly.app2.ui.model.WeekWeatherModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GismeteoSourceData(val mapper: WeatherMapper) {
    private val gisApi by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.nopbreak.ru/")
            .build()

        retrofit.create(GismeteoApi::class.java)
    }

    fun getWeather(cityId: Int): Single<WeatherModel> {
        return gisApi.getCurrentWeather(cityId).subscribeOn(Schedulers.io()).map(mapper::map)
    }

    fun getWeekWeather(cityId: Int): Single<List<WeekWeatherModel>> {
        return gisApi.getWeekWeather(cityId).subscribeOn(Schedulers.io()).map(mapper::map)
    }

    fun getLocation(city: String): Single<List<LocationModel>> {
        return gisApi.getLocation(city).subscribeOn(Schedulers.io()).map(mapper::map).retry(3)
    }
}
