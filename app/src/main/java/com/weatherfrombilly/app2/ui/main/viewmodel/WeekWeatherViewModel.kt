package com.weatherfrombilly.app2.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.data.model.WeekWeatherModel
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable

class WeekWeatherViewModel(val repo: WeatherRepository = WeatherRepository()) : ViewModel() {
    private val disposables = CompositeDisposable()

    val models = MutableLiveData<List<WeekWeatherModel>>()

    private fun fetchData() {
        disposables.add(
            repo.getWeekWeather().subscribe({ model ->
                models.postValue(model)
            }, {
                it.printStackTrace()
            })
        )
    }

    fun onResume() {
        fetchData()
    }
}