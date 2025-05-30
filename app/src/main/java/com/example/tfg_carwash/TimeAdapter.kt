package com.example.tfg_carwash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private val times = mutableListOf<String>()

    fun updateTimes(newTimes: List<String>) {
        times.clear()
        times.addAll(newTimes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position])
    }

    override fun getItemCount(): Int = times.size

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(time: String) {
            textView.text = time
        }
    }
}