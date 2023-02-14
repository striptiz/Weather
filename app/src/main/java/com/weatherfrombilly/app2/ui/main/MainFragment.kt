package com.weatherfrombilly.app2.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.ui.main.model.net.NetworkService
import com.weatherfrombilly.app2.ui.main.model.net.model.CurrentWeatherResponse
import com.weatherfrombilly.app2.ui.main.ui.WeatherDayAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), Callback<CurrentWeatherResponse> {
    private lateinit var recyclerView: RecyclerView
    private lateinit var windSpeed: TextView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val service = NetworkService()
        service.tomorrowApi.getCurrentWeather().enqueue(this)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragment = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = fragment.findViewById(R.id.fragment_main__rv)
        windSpeed = fragment.findViewById(R.id.textView5)
        recyclerView.adapter = WeatherDayAdapter()
        return fragment
    }

    override fun onResponse(
        call: Call<CurrentWeatherResponse>,
        response: Response<CurrentWeatherResponse>
    ) {
        Log.d("res", response.body()?.data?.values.toString() ?: "empty")
        windSpeed.text = response.body()?.data?.values?.windSpeed.toString() ?: "error"
    }

    override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
        t.printStackTrace()
    }
}