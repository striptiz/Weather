package com.weatherfrombilly.app2.ui.main.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.ui.main.model.WeatherModel
import com.weatherfrombilly.app2.ui.main.adapter.WeatherIconAdapter
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentVM : ViewModel() {
    val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    val _temp = MutableLiveData<String>()
    val temp: LiveData<String>
        get() = _temp

    val _wind = MutableLiveData<String>()
    val wind: LiveData<String>
        get() = _wind

    val _humidity = MutableLiveData<String>()
    val humidity: LiveData<String>
        get() = _humidity

    val _desc = MutableLiveData<String>()
    val desc: LiveData<String>
        get() = _desc

    val _icon = MutableLiveData<Int>()
    val icon: LiveData<Int>
        get() = _icon

    fun updateData(model: WeatherModel) {
        _cityName.postValue(model.cityName)
        _wind.postValue(model.windSpeed.toString())
        _temp.postValue("${model.temperature}°")
        _humidity.postValue(model.humidity.toString())
        _desc.postValue(model.desc)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        _date.postValue(format.format(model.date))
        _icon.postValue(WeatherIconAdapter.getIcon(model.iconModel.id))
    }
}