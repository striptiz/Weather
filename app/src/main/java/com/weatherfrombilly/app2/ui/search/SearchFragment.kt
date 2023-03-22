package com.weatherfrombilly.app2.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherfrombilly.app2.activity.MainViewModel
import com.weatherfrombilly.app2.activity.MainViewModelFactory
import com.weatherfrombilly.app2.data.mapper.WeatherMapper
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import com.weatherfrombilly.app2.data.source.GismeteoSourceData
import com.weatherfrombilly.app2.databinding.FragmentSearchBinding
import com.weatherfrombilly.app2.ui.adapter.CitiesAdapter
import com.weatherfrombilly.app2.ui.model.LocationModel

class SearchFragment() : Fragment(), CitiesAdapter.ClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val vm: SearchFragmentVM by viewModels { SearchFragmentViewModelFactory(requireContext()) }
    private val mainVm: MainViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory(requireContext()) }
    )

    private val citiesAdapter =
        CitiesAdapter(WeatherRepository(GismeteoSourceData(WeatherMapper())), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = citiesAdapter
        }
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.onTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.modelData.observe(viewLifecycleOwner) {
            citiesAdapter.setData(it.cities)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(model: LocationModel) {
        Toast.makeText(requireContext(), "Clicked: $model", Toast.LENGTH_SHORT).show()
        vm.onCityClicked(model)
        mainVm.onCityChanged(model.cityId)
        findNavController().popBackStack()
    }
}