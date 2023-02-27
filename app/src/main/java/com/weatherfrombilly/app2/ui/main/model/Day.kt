package com.weatherfrombilly.app2.ui.main.model

import android.content.Context

enum class Day(private val id: Int) {
    SUNDAY(com.weatherfrombilly.app2.R.string.sunday),
    MONDAY(com.weatherfrombilly.app2.R.string.monday),
    TUESDAY(com.weatherfrombilly.app2.R.string.tuesday),
    WEDNESDAY(com.weatherfrombilly.app2.R.string.wednesday),
    THURSDAY(com.weatherfrombilly.app2.R.string.thursday),
    FRIDAY(com.weatherfrombilly.app2.R.string.friday),
    SATURDAY(com.weatherfrombilly.app2.R.string.saturday);

    fun toLocalizedFullNamedString(context: Context): String {
        return context.getString(id)
    }
}