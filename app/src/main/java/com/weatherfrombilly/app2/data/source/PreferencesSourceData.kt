package com.weatherfrombilly.app2.data.source

import android.content.Context
import androidx.preference.PreferenceManager

class PreferencesSourceData(private val context: Context) {
    private val prefManager = PreferenceManager.getDefaultSharedPreferences(context)

    fun <T> getData(key: String, default: T): T {
        return default
    }

    fun getString(key: String, default: String): String {
        return prefManager.getString(key, default) ?: default
    }

    fun saveString(key: String, value: String) {
        prefManager.edit().putString(key, value).apply()
    }

    fun getInt(key: String, default: Int): Int {
        return prefManager.getInt(key, default) ?: default
    }

    fun saveInt(key: String, value: Int) {
        prefManager.edit().putInt(key, value).apply()
    }

    fun <T> saveData(key: String, data: T) {}
}