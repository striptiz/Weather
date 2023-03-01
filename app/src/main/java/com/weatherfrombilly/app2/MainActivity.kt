package com.weatherfrombilly.app2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.weatherfrombilly.app2.databinding.ActivityMainBinding
import com.weatherfrombilly.app2.ui.main.viewmodel.MainViewModel
import com.weatherfrombilly.app2.ui.main.viewmodel.UiState

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.model = viewModel
        binding.lifecycleOwner = this
        viewModel.state.observe(this) { state ->
            if (state == UiState.LOADED) {
                binding.swiperefresh.isRefreshing = false
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}