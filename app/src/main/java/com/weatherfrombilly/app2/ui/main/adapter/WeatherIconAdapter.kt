package com.weatherfrombilly.app2.ui.main.adapter

import com.weatherfrombilly.app2.R

class WeatherIconAdapter {
    enum class Icon(val iconId: String, val id: Int) {
        CLOUD("cloud", R.drawable.ic_weather_cloud),
        FOG("fog", R.drawable.ic_weather_fog),
        THUNDER("thunder", R.drawable.ic_weather_thunder),
        LIGHT_CLOUDS("light_cloud", R.drawable.ic_weather_light_clouds),
        RAIN("rain", R.drawable.ic_weather_rain),
        RAINFALL("rainfall", R.drawable.ic_weather_rainfall),
        SNOWFALL("snowfall", R.drawable.ic_weather_snowfall),
        SUN("sun", R.drawable.ic_weather_sun),
        HAIL("hail", R.drawable.ic_weather_hail),
        HURRICANE("hurricane", R.drawable.ic_weather_hurricane),
    }

    fun getIcon(iconId: String): Int {
        return Icon.values().find { it.iconId == iconId }?.id ?: Icon.CLOUD.id
    }
}