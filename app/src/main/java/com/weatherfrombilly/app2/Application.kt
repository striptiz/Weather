package com.weatherfrombilly.app2

import android.annotation.SuppressLint
import android.content.Context

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }
}