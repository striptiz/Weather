package com.weatherfrombilly.app2.ui.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.weatherfrombilly.app2.R


class LocationDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(
            R.layout.search_location_dialog,
            container,
            false
        )

        return v
    }

    companion object {
        fun newInstance(): LocationDialog {
            return LocationDialog()
        }
    }
}