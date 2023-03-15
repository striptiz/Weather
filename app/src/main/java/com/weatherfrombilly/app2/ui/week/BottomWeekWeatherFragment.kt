package com.weatherfrombilly.app2.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.databinding.FragmentBottomWeekWeatherBinding
import com.weatherfrombilly.app2.activity.MainViewModel
import com.weatherfrombilly.app2.ui.adapter.WeatherDayAdapter
import com.weatherfrombilly.app2.activity.MainViewModelFactory
import com.weatherfrombilly.app2.ui.model.MainUiState
import com.weatherfrombilly.app2.ui.model.WeekWeatherModel
import com.weatherfrombilly.app2.util.UI.isLandscapeOrientation

class BottomWeekWeatherFragment : Fragment() {
    private var _binding: FragmentBottomWeekWeatherBinding? = null
    private val binding: FragmentBottomWeekWeatherBinding
        get() = _binding!!

    private var _adapter: WeatherDayAdapter? = null
    private val adapter: WeatherDayAdapter
        get() = _adapter!!

    private val mainVm: MainViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomWeekWeatherBinding.inflate(inflater, container, false)
        _adapter = WeatherDayAdapter(object : WeatherDayAdapter.ClickListener{
            override fun onWeatherClicked(wDay: WeekWeatherModel) {
                mainVm.onWeatherClicked(wDay)
            }
        })

        binding.fragmentMainRv.adapter = adapter
        binding.fragmentMainRv.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = if (!requireContext().isLandscapeOrientation()) {
                RecyclerView.HORIZONTAL
            } else {
                RecyclerView.VERTICAL
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainVm.state.observe(viewLifecycleOwner) {
            if (it is MainUiState.LOADED) {
                adapter.setData(it.weekWeatherData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}