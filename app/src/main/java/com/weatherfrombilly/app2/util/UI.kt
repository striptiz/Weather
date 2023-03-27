package com.weatherfrombilly.app2.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.weatherfrombilly.app2.util.UI.hideActionBar

object UI {
    fun Context.isLandscapeOrientation(): Boolean =
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun show(vararg views: View) {
        views.forEach { it.visibility = View.VISIBLE }
    }

    fun hide(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }

    fun Activity.showActionBar() {
        (this as AppCompatActivity).supportActionBar?.show()
    }

    fun Activity.hideActionBar() {
        (this as AppCompatActivity).supportActionBar?.hide()
    }
}