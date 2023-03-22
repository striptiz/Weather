package com.weatherfrombilly.app2.data.repository

import com.weatherfrombilly.app2.data.source.PreferencesSourceData

class PreferencesRepository(private val prefSource: PreferencesSourceData) {
    fun getCurrentCityId(): Int {
        return prefSource.getInt("current_city", 11878)
    }

    fun saveCurrentCityId(cityId: Int) {
        prefSource.saveInt("current_city", cityId)
    }

    fun getTemperatureFormat(): Boolean {
        return prefSource.getString("temperature_format", "c") == "c"
    }

    fun getRequestTimeout(): Int {
        return prefSource.getInt("request_timeout", 2000)
    }
}