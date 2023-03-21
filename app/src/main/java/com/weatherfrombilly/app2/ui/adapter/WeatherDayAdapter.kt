package com.weatherfrombilly.app2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherfrombilly.app2.R
import com.weatherfrombilly.app2.data.repository.PreferencesRepository
import com.weatherfrombilly.app2.databinding.ViewHolderDayBinding
import com.weatherfrombilly.app2.ui.model.WeekWeatherModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherDayAdapter(
    private val listener: ClickListener,
    private val repository: PreferencesRepository
) : RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder>() {
    private val days = mutableListOf<WeekWeatherModel>()

    interface ClickListener {
        fun onWeatherClicked(wDay: WeekWeatherModel)
    }

    inner class WeatherDayViewHolder(private val binding: ViewHolderDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (pos >= 0) {
                    listener.onWeatherClicked(days[pos])
                }
            }
        }

        fun bind(wDay: WeekWeatherModel) {
            var temp = wDay.temperature

            if (!repository.getTemperatureFormat()) {
                temp = toF(temp)
            }

            binding.temp.text = itemView.resources.getString(R.string.temp_format, temp)
            binding.desc.text = wDay.desc
            binding.weatherIcon.setImageResource(WeatherIconAdapter.getIcon(wDay.icon.id))
            binding.day.text = getNamedDay(wDay.date)
            val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
            binding.date.text = format.format(wDay.date)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(l: List<WeekWeatherModel>) {
        days.clear()
        days.addAll(l)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDayViewHolder {
        val itemBinding = ViewHolderDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherDayViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherDayViewHolder, position: Int) {
        val wDay = days.get(position)
        holder.bind(wDay)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    companion object {
        private fun getNamedDay(date: Date): String {
            val cal: Calendar = Calendar.getInstance().apply {
                time = date
            }
            val dayOfWeek: Int = cal.get(Calendar.DAY_OF_WEEK)
            return DateFormatSymbols().shortWeekdays[dayOfWeek]
        }
    }

    fun toF(temp: Int): Int {
        return (9 / 5f * temp).roundToInt() + 32
    }
}