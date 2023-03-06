package com.weatherfrombilly.app2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.weatherfrombilly.app2.databinding.ActivityMainBinding
import com.weatherfrombilly.app2.ui.main.adapter.WeatherDayAdapter
import com.weatherfrombilly.app2.ui.main.viewmodel.MainViewModel
import com.weatherfrombilly.app2.ui.main.viewmodel.UiState
import com.weatherfrombilly.app2.ui.main.viewmodel.WeekWeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvViewModel: WeekWeatherViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherDayAdapter
    private lateinit var lm: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        rvViewModel = ViewModelProvider(this)[WeekWeatherViewModel::class.java]
        binding.model = viewModel
        binding.lifecycleOwner = this
        viewModel.state.observe(this) { state ->
            renderState(state)
        }
        rvViewModel.models.observe(this) { state ->
            adapter.setData(state)
        }

        binding.updateButton.setOnClickListener {
            viewModel.onRefresh()
        }
        adapter = WeatherDayAdapter()
        lm = LinearLayoutManager(this).apply {
            orientation = HORIZONTAL
        }
        binding.fragmentMainRv.adapter = adapter
        binding.fragmentMainRv.layoutManager = lm

//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.onRefresh()
//        }
    }

    private fun renderState(state: UiState) {
        when (state) {
            UiState.ERROR -> {
                hideMain()
                showError()
                // binding.swipeRefresh.isRefreshing = false
                hide(binding.activityMainProgress)
            }
            UiState.LOADED -> {
                showMain()
                //binding.swipeRefresh.isRefreshing = false
                hide(binding.activityMainProgress)
            }
            UiState.LOADING -> {
                hideMain()
                show(binding.activityMainProgress)
            }
            UiState.START -> {
                hideMain()
                show(binding.activityMainProgress)
            }
        }
    }

    private fun showError() {
        binding.apply {
            show(
                activityMainErrorLabel,
                updateButton
            )
        }
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
                activityMainProgress,
                activityMainErrorLabel,
                updateButton
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
        rvViewModel.onResume()
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
