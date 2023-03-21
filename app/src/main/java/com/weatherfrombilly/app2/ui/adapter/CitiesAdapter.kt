package com.weatherfrombilly.app2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weatherfrombilly.app2.data.repository.WeatherRepository
import com.weatherfrombilly.app2.databinding.ViewHolderCityBinding
import com.weatherfrombilly.app2.ui.model.LocationModel

class CitiesAdapter(
    private val repository: WeatherRepository,
    private val listener: ClickListener
) :
    Adapter<CitiesAdapter.CityViewHolder>() {
    private val cities = mutableListOf<LocationModel>()

    interface ClickListener {
        fun onClicked(model: LocationModel)
    }

    inner class CityViewHolder(val binding: ViewHolderCityBinding) : ViewHolder(binding.root) {
        fun onBind(model: LocationModel) {
            binding.cityName.text = model.cityName
            binding.cityDesc.text = model.fullName
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }
    }

    private fun onClicked(pos: Int) {
        if (pos != RecyclerView.NO_POSITION) {
            listener.onClicked(cities.get(pos))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemBinding = ViewHolderCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.onBind(cities.get(position))
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setData(data: List<LocationModel>) {
        cities.clear()
        cities.addAll(data)
        notifyDataSetChanged()
    }
}