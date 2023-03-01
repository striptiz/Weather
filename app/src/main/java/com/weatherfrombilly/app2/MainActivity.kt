package com.weatherfrombilly.app2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.weatherfrombilly.app2.databinding.ActivityMainBinding
import com.weatherfrombilly.app2.ui.main.viewmodel.MainViewModel
import com.weatherfrombilly.app2.ui.main.viewmodel.UiState

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.model = viewModel
        binding.lifecycleOwner = this
        viewModel.state.observe(this) { state ->
            renderState(state)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun renderState(state: UiState) {
        when (state) {
            UiState.ERROR -> {
                hideMain()
                showError()
                binding.swipeRefresh.isRefreshing = false
                hide(binding.activityMainProgress)
            }
            UiState.LOADED -> {
                showMain()
                binding.swipeRefresh.isRefreshing = false
                hide(binding.activityMainProgress)
            }
            UiState.LOADING -> {}
            UiState.START -> {
                hideMain()
                show(binding.activityMainProgress)
            }
        }
    }

    private fun showError() {
    }

    private fun showMain() {
        binding.apply {
            show(
                cityNameTV,
                weatherIconIV,
                weatherDescTV,
                currentTempTV,
                tempIV,
                rainIV,
                currentRainIntencityTV,
                windIconIV,
                currentWindSpeedTV,
                activityMainProgress
            )
        }
    }

    private fun hideMain() {
        binding.apply {
            hide(
                cityNameTV,
                weatherIconIV,
                weatherDescTV,
                currentTempTV,
                tempIV,
                rainIV,
                currentRainIntencityTV,
                windIconIV,
                currentWindSpeedTV,
                activityMainProgress
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    companion object {
        private fun show(vararg views: View) {
            views.forEach { it.visibility = View.VISIBLE }
        }

        private fun hide(vararg views: View) {
            views.forEach { it.visibility = View.GONE }
        }
    }
}
