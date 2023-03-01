package com.weatherfrombilly.app2.ui.main.viewmodel

sealed class UiState {
    object START: UiState()
    object LOADING: UiState()
    object LOADED: UiState()
    object ERROR: UiState()

    companion object {
        @JvmStatic
        fun isMainVisible(model: UiState) {

        }
    }
}