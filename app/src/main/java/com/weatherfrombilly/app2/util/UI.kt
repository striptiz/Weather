package com.weatherfrombilly.app2.util

import android.content.Context
import android.content.res.Configuration

object UI {
    fun Context.isLandscapeOrientation(): Boolean =
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}