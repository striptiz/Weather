package com.weatherfrombilly.app2.data.source

class PreferencesSourceData() {
    fun <T> getData(key: String, default: T): T {
        return default
    }

    fun <T> saveData(key: String, data: T) {}
}