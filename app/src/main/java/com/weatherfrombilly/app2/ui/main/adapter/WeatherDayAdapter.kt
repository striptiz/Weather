package com.weatherfrombilly.app2.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.data.model.WeekWeatherModel
import com.weatherfrombilly.app2.ui.main.model.WeatherDay

class WeatherDayAdapter : RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder>() {
    private val days = mutableListOf<WeekWeatherModel>()

    class WeatherDayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<ImageView>(R.id.view_holder_day__icon)
        private val day = view.findViewById<TextView>(R.id.view_holder_day__day)
        private val temperature = view.findViewById<TextView>(R.id.view_holder_day__temperature)
        private val desc = view.findViewById<TextView>(R.id.view_holder_day__desc)

        fun bind(wDay: WeekWeatherModel) {
            temperature.text = "${wDay.temperature}°"
            desc.text = wDay.desc
            //day.text = wDay.day.toLocalizedFullNamedString(itemView.context)
            //icon.setImageResource(wDay.icon.id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(l: List<WeekWeatherModel>) {
        days.addAll(l)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDayViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_day, null, false)
        return WeatherDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDayViewHolder, position: Int) {
        val wDay = days.get(position)
        holder.bind(wDay)
    }

    override fun getItemCount(): Int {
        return days.size
    }
}