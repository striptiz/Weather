package com.weatherfrombilly.app2.ui.main.model.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    val tomorrowApi by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tomorrow.io/")
            .build()

        retrofit.create(TomorrowApi::class.java)
    }
}