package com.weatherfrombilly.app2.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherfrombilly.app2.data.repository.PreferencesRepository
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import com.weatherfrombilly.app2.ui.model.MainUiState
import com.weatherfrombilly.app2.ui.model.WeatherModel
import com.weatherfrombilly.app2.ui.model.WeekWeatherModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val repo: WeatherRepository,
    private val prefRepo: PreferencesRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _state = MutableLiveData<MainUiState>().apply {
        value = MainUiState.START
    }

    val state: LiveData<MainUiState>
        get() = _state

    private val _cityId = MutableLiveData<Int>()
    val cityId: LiveData<Int>
        get() = _cityId

    private val _event = MutableLiveData<ActionEvent>()
    val event: LiveData<ActionEvent>
        get() = _event

    private fun fetchData() {
        disposables.add(
            repo.getWeather(prefRepo.getCurrentCityId())
                .delay(prefRepo.getRequestTimeout().toLong(), TimeUnit.MILLISECONDS)
                .zipWith(repo.getWeekWeather(prefRepo.getCurrentCityId())) { current, week ->
                    MainUiState.LOADED(
                        currentWeatherData = current,
                        weekWeatherData = week.filter { // FIXME: move logic to domain
                            val calendar = Calendar.getInstance()
                            calendar.time = it.date

                            val t = calendar.get(Calendar.HOUR_OF_DAY)

                            t > 12 && t < 18
                        })
                }.subscribe({ state ->
                    _state.postValue(state)
                }, {
                    it.printStackTrace()
                    _state.postValue(MainUiState.ERROR("data"))
                })
        )
    }

    fun maybeUpdateData() {
        when (state.value) {
            is MainUiState.START, is MainUiState.ERROR, null -> {
                tryLoadData()
            }
            else -> {}
        }
    }

    fun updateData() {
        tryLoadData()
    }

    private fun tryLoadData() {
        _state.postValue(MainUiState.LOADING)
        fetchData()
    }

    fun onCityChanged(cityId: Int) {
        _state.postValue(MainUiState.LOADING)
        fetchData()
    }

    fun onWeatherClicked(wDay: WeekWeatherModel) {
        state.value?.let { state ->
            if (state is MainUiState.LOADED) {
                _state.postValue(
                    MainUiState.LOADED(
                        currentWeatherData = wDay.toWeatherData(state.currentWeatherData),
                        weekWeatherData = state.weekWeatherData
                    )
                )
            }
        }
    }

    fun showSettings() {
        _event.postValue(ActionEvent.SHOW_SETTINGS)
    }
}

private fun WeekWeatherModel.toWeatherData(currentWeatherData: WeatherModel): WeatherModel {
    return currentWeatherData.copy(
        temperature = temperature,
        date = date,
        desc = desc,
        iconModel = icon,
        humidity = humidity,
        windSpeed = windSpeed.toFloat(),
        temperature_min = tempMin,
        temperature_max = tempMax
    )
}
