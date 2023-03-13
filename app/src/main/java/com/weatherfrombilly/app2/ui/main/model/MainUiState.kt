package com.weatherfrombilly.app2.ui.main.model

sealed class MainUiState {
    object START : MainUiState()
    object LOADING : MainUiState()

    data class LOADED(
        val currentWeatherData: WeatherModel,
        val weekWeatherData: List<WeekWeatherModel>
    ) : MainUiState()

    data class ERROR(val desc: String) : MainUiState()
}