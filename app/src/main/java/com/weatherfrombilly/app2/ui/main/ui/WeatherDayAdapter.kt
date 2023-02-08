package com.weatherfrombilly.app2.ui.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.R

class WeatherDayAdapter : RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder>() {
    private val days = listOf<WeatherDay>(
        WeatherDay(0, Day.MONDAY, Icon.THUNDER),
        WeatherDay(2, Day.TUESDAY, Icon.THUNDER),
        WeatherDay(5, Day.WEDNESDAY, Icon.THUNDER),
        WeatherDay(-7, Day.THURSDAY, Icon.THUNDER)
    )

    class WeatherDayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<ImageView>(R.id.view_holder_day__icon)
        private val day = view.findViewById<TextView>(R.id.view_holder_day__day)
        private val temperature = view.findViewById<TextView>(R.id.view_holder_day__temperature)

        fun bind(wDay: WeatherDay) {
            temperature.text = "${wDay.temp}°"
            day.text = wDay.day.toLocalizedFullNamedString(itemView.context)
            icon.setImageResource(wDay.icon.id)
        }
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