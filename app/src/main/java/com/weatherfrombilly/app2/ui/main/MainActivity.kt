package com.weatherfrombilly.app2.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.databinding.ActivityMainBinding
import com.weatherfrombilly.app2.ui.main.adapter.WeatherDayAdapter
import com.weatherfrombilly.app2.ui.main.factory.MainViewModelFactory
import com.weatherfrombilly.app2.ui.main.model.MainUiState
import com.weatherfrombilly.app2.ui.main.view.main.MainFragment
import com.weatherfrombilly.app2.util.UI.hide
import com.weatherfrombilly.app2.util.UI.isLandscapeOrientation

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val vm: MainViewModel by viewModels { MainViewModelFactory }

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: WeatherDayAdapter
    private lateinit var lm: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentContainer, MainFragment())
            }
        }
        adapter = WeatherDayAdapter()
        lm = LinearLayoutManager(this).apply {
            orientation = if (!applicationContext.isLandscapeOrientation()) {
                RecyclerView.HORIZONTAL
            } else {
                RecyclerView.VERTICAL
            }
        }
        binding.fragmentMainRv.adapter = adapter
        binding.fragmentMainRv.layoutManager = lm
        binding.updateButton.setOnClickListener {
            vm.onUpdateClicked()
        }
        binding.refresh.setOnRefreshListener(this)

        observeUiChanges()
    }

    private fun observeUiChanges() {
        vm.state.observe(this) {
            hideAll()
            if (it !is MainUiState.LOADED) {
                binding.fragmentContainer.visibility = View.GONE
                binding.fragmentMainRv.visibility = View.INVISIBLE
            } else {
                adapter.setData(it.weekWeatherData)
                binding.fragmentContainer.visibility = View.VISIBLE
                binding.fragmentMainRv.visibility = View.VISIBLE
                binding.refresh.isRefreshing = false
            }

            if (it is MainUiState.LOADING) {
                showLoading()
            } else {
                hideLoading()
            }

            if (it is MainUiState.ERROR) {
                showError(it.desc)
            } else {
                hideError()
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            hide(fragmentContainer, activityMainProgress, activityMainErrorLabel, updateButton)
        }
    }

    private fun showError(desc: String) {
        binding.activityMainErrorLabel.text = desc
        binding.activityMainErrorLabel.visibility = View.VISIBLE
        binding.updateButton.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.activityMainErrorLabel.visibility = View.GONE
        binding.updateButton.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.activityMainProgress.visibility = View.GONE
    }

    private fun showLoading() {
        binding.activityMainProgress.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        vm.onUpdateClicked()
    }

    override fun onRefresh() {
        vm.startRefresh()
    }
}
