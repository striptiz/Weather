@file:Suppress("UNCHECKED_CAST")

package com.weatherfrombilly.app2.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.weatherfrombilly.app2.data.mapper.WeatherMapper
import com.weatherfrombilly.app2.data.repository.PreferencesRepository
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import com.weatherfrombilly.app2.data.source.GismeteoSourceData
import com.weatherfrombilly.app2.data.source.PreferencesSourceData

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel(
            WeatherRepository(
                GismeteoSourceData(
                    WeatherMapper()
                )
            ),
            PreferencesRepository(
                PreferencesSourceData(context)
            )
        ) as T
    }
}