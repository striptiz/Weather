package com.weatherfrombilly.app2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.databinding.FragmentHomeBinding
import com.weatherfrombilly.app2.activity.MainViewModel
import com.weatherfrombilly.app2.activity.MainViewModelFactory
import com.weatherfrombilly.app2.ui.model.MainUiState
import com.weatherfrombilly.app2.ui.main.MainFragment
import com.weatherfrombilly.app2.ui.week.BottomWeekWeatherFragment
import com.weatherfrombilly.app2.util.UI.hide
import com.weatherfrombilly.app2.util.UI.show

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

        childFragmentManager.beginTransaction().add(R.id.main_weather_container, MainFragment())
            .commit();
        childFragmentManager.beginTransaction()
            .add(R.id.week_weather_container, BottomWeekWeatherFragment()).commit();
    }

    fun showContent() {
        binding.apply {
            show(mainWeatherContainer, weekWeatherContainer)
        }
    }

    fun hideContent() {
        binding.apply {
            hide(mainWeatherContainer, weekWeatherContainer)
        }
    }

    fun showProgress() {
        binding.apply {
            show(activityMainProgress)
        }
    }

    fun hideProgress() {
        binding.apply {
            hide(activityMainProgress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}