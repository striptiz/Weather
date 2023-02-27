package com.weatherfrombilly.app2.ui.main.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.data.repository.WeatherRepository

class MainViewModel(val repo: WeatherRepository = WeatherRepository()) : ViewModel() {
    val city = MutableLiveData<String>()
}