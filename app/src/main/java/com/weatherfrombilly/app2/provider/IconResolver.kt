package com.weatherfrombilly.app2.provider

import com.weatherfrombilly.app2.ui.adapter.WeatherIconAdapter
import com.weatherfrombilly.app2.ui.model.IconModel

class IconResolver {
    fun getIconModel(desc: String): IconModel {
        return IconModel(
            when (val norm = desc.lowercase()) {
                "малооблачно" -> WeatherIconAdapter.Icon.LIGHT_CLOUDS
                "ясно" -> WeatherIconAdapter.Icon.SUN
                "пасмурно" -> WeatherIconAdapter.Icon.CLOUD
                else -> if (norm.contains("небольшой дождь")) {
                    WeatherIconAdapter.Icon.RAIN
                } else {
                    WeatherIconAdapter.Icon.CLOUD
                }
            }.iconId
        )
    }
}