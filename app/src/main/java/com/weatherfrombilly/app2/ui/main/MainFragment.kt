package com.weatherfrombilly.app2.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.ui.main.ui.WeatherDayAdapter

class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragment = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = fragment.findViewById(R.id.fragment_main__rv)
        recyclerView.adapter = WeatherDayAdapter()
        return fragment
    }
}