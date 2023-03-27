package com.weatherfrombilly.app2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.activity.MainViewModel
import com.weatherfrombilly.app2.activity.MainViewModelFactory
import com.weatherfrombilly.app2.databinding.FragmentHomeBinding
import com.weatherfrombilly.app2.ui.main.MainFragment
import com.weatherfrombilly.app2.ui.model.MainUiState
import com.weatherfrombilly.app2.ui.week.BottomWeekWeatherFragment
import com.weatherfrombilly.app2.util.UI.hide
import com.weatherfrombilly.app2.util.UI.hideActionBar
import com.weatherfrombilly.app2.util.UI.show
import com.weatherfrombilly.app2.util.UI.showActionBar

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val mainVm: MainViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory(requireContext()) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVm.state.observe(viewLifecycleOwner) {
            if (it is MainUiState.LOADED) {
                showContent()
                hideProgress()
            } else {
                showProgress()
                hideContent()
            }
        }

        binding.updateAction.setOnClickListener {
            mainVm.updateData()
        }

        binding.menuAction.setOnClickListener {
            mainVm.showSettings()
        }
        mainVm.state.observe(viewLifecycleOwner) {
            if (it is MainUiState.LOADED) {
                show(binding.updateAction, binding.menuAction)
            } else {
                hide(binding.updateAction, binding.menuAction)
            }
        }

        childFragmentManager.beginTransaction().add(R.id.main_weather_container, MainFragment())
            .commit();
        childFragmentManager.beginTransaction()
            .add(R.id.week_weather_container, BottomWeekWeatherFragment()).commit()
    }

    private fun showContent() {
        binding.apply {
            show(mainWeatherContainer, weekWeatherContainer)
        }
    }

    private fun hideContent() {
        binding.apply {
            hide(mainWeatherContainer, weekWeatherContainer)
        }
    }

    private fun showProgress() {
        binding.apply {
            show(activityMainProgress)
        }
    }

    private fun hideProgress() {
        binding.apply {
            hide(activityMainProgress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        requireActivity().hideActionBar()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().showActionBar()
    }
}