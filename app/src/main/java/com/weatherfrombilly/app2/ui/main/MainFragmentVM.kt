package com.weatherfrombilly.app2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.Application
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.data.repository.PreferencesRepository
import com.weatherfrombilly.app2.data.source.PreferencesSourceData
import com.weatherfrombilly.app2.ui.adapter.WeatherIconAdapter
import com.weatherfrombilly.app2.ui.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainFragmentVM : ViewModel() {
    private val repository: PreferencesRepository = PreferencesRepository(
        PreferencesSourceData(
            Application.getContext() // TODO: __FIX_ME
        )
    )

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

    val _min_temp = MutableLiveData<String>()
    val min_temp: LiveData<String>
        get() = _min_temp

    val _max_temp = MutableLiveData<String>()
    val max_temp: LiveData<String>
        get() = _max_temp

    val _current_temp_icon = MutableLiveData<Int>()
    val current_temp_icon: LiveData<Int>
        get() = _current_temp_icon

    fun updateData(model: WeatherModel) {
        _cityName.postValue(model.cityName)
        _wind.postValue(model.windSpeed.toString())
        _humidity.postValue(model.humidity.toString())
        _desc.postValue(model.desc)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        _date.postValue(format.format(model.date))
        _icon.postValue(WeatherIconAdapter.getIcon(model.iconModel.id))
        updateTemperature(model)
    }

    private fun updateTemperature(model: WeatherModel) {
        var currentTemp = model.temperature
        var minTemp = model.temperature_min
        var maxTemp = model.temperature_max

        if (!repository.getTemperatureFormat()) {
            currentTemp = toF(currentTemp)
            minTemp = toF(minTemp)
            maxTemp = toF(maxTemp)
            _current_temp_icon.postValue(R.drawable.ic_temperature_icon_f)
        } else {
            _current_temp_icon.postValue(R.drawable.ic_temperature_icon_c)
        }

        _temp.postValue("$currentTemp")
        _min_temp.postValue("$minTemp")
        _max_temp.postValue("$maxTemp")
    }

    fun toF(temp: Int): Int {
        return (9 / 5f * temp).roundToInt() + 32
    }
}