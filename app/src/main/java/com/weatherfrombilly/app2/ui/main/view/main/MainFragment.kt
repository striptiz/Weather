package com.weatherfrombilly.app2.ui.main.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.weatherfrombilly.app2.databinding.MainWeatherFragmentBinding
import com.weatherfrombilly.app2.ui.main.MainViewModel
import com.weatherfrombilly.app2.ui.main.factory.MainViewModelFactory
import com.weatherfrombilly.app2.ui.main.model.MainUiState

class MainFragment : Fragment() {
    private var _binding: MainWeatherFragmentBinding? = null
    private val binding get() = _binding!!
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
        _binding = MainWeatherFragmentBinding.inflate(inflater, container, false)
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