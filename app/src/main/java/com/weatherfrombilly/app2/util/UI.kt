package com.weatherfrombilly.app2.util

import android.content.Context
import android.content.res.Configuration
import android.view.View

object UI {
    fun Context.isLandscapeOrientation(): Boolean =
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun show(vararg views: View) {
        views.forEach { it.visibility = View.VISIBLE }
    }

    fun hide(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }
}