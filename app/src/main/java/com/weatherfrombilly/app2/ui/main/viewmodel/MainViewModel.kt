package com.weatherfrombilly.app2.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(val repo: WeatherRepository = WeatherRepository()) : ViewModel() {
    private val disposables = CompositeDisposable()

    val city = MutableLiveData<String>()
    val currentWindSpeed = MutableLiveData<String>()
    val currentTemp = MutableLiveData<String>()
    val currentHumidity = MutableLiveData<String>()
    val desc = MutableLiveData<String>()
    val date = MutableLiveData<String>()

    val state = MutableLiveData<UiState>().apply {
        value = UiState.START
    }

    private fun fetchData() {
        disposables.add(
            repo.getWeather().subscribe({ model ->
                city.postValue(model.cityName)
                currentWindSpeed.postValue(model.windSpeed.toString())
                currentTemp.postValue("${model.temperature}°")
                currentHumidity.postValue(model.humidity.toString())
                desc.postValue(model.desc)
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                date.postValue(format.format(model.date))
                state.postValue(UiState.LOADED)
            }, {
                it.printStackTrace()
                state.postValue(UiState.ERROR)
            })
        )
    }

    fun onResume() {
        fetchData()
    }

    fun onRefresh() {
        if (state.value != UiState.START) {
            state.value = UiState.LOADING
        }
        fetchData()
    }
}