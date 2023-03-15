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

    private fun fetchData() {
        disposables.add(
            repo.getWeather(prefRepo.getCurrentCityId())
                .zipWith(repo.getWeekWeather(11878)) { current, week ->
                    MainUiState.LOADED(
                        currentWeatherData = current,
                        weekWeatherData = week.filter { // FIXME: move logic to domain
                            val calendar = Calendar.getInstance()
                            calendar.time = it.date

                            calendar.get(Calendar.HOUR_OF_DAY) == 15
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

    private fun tryLoadData() {
        _state.postValue(MainUiState.LOADING)
        fetchData()
    }

    fun startRefresh() {
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
}

private fun WeekWeatherModel.toWeatherData(currentWeatherData: WeatherModel): WeatherModel {
    return currentWeatherData.copy(
        temperature = temperature,
        date = date,
        desc = desc,
        iconModel = icon,
        humidity = humidity,
        windSpeed = windSpeed.toFloat()
    )
}
