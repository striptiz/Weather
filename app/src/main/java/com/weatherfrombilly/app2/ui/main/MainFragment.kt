package com.weatherfrombilly.app2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.weatherfrombilly.app2.databinding.FragmentMainWeatherBinding
import com.weatherfrombilly.app2.activity.MainViewModel
import com.weatherfrombilly.app2.activity.MainViewModelFactory
import com.weatherfrombilly.app2.ui.model.MainUiState

class MainFragment : Fragment() {
    private var _binding: FragmentMainWeatherBinding? = null
    private val binding: FragmentMainWeatherBinding
        get() = _binding!!

    private val vm: MainFragmentVM by viewModels()
    private val mainVm: MainViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainWeatherBinding.inflate(inflater, container, false)
        binding.model = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVm.state.observe(viewLifecycleOwner) {
            renderState(it)
        }
        vm.icon.observe(viewLifecycleOwner) {
            binding.weatherIcon.setImageResource(it)
        }
    }

    private fun renderState(it: MainUiState) {
        if (it is MainUiState.LOADED) {
            vm.updateData(it.currentWeatherData)
            Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}