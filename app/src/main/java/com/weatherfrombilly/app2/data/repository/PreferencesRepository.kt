package com.weatherfrombilly.app2.data.repository

import com.weatherfrombilly.app2.data.source.PreferencesSourceData

class PreferencesRepository(private val prefSource: PreferencesSourceData = PreferencesSourceData()) {
    fun getCurrentCityId(): Int {
        return prefSource.getData("current_city", 11878)
    }

    fun saveCurrentCityId(cityId: Int) {
        prefSource.saveData("current_city", cityId)
    }
}