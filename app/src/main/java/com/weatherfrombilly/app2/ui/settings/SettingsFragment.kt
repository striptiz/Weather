package com.weatherfrombilly.app2.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.weatherfrombilly.app2.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}