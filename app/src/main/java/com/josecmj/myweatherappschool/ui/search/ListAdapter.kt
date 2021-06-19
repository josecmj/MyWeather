package com.josecmj.myweatherappschool.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.josecmj.myweatherappschool.R
import com.josecmj.myweatherappschool.model.Place


class ListAdapter(private val onClick: (Place) -> Unit)  :
    ListAdapter<Place, com.josecmj.myweatherappschool.ui.search.ListAdapter.ListItemViewHolder>(ItemDiffCallback) {


    class ListItemViewHolder(itemView: View, val onClick: (Place) -> Unit) :
        RecyclerView.ViewHolder(itemView)  {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.txtCityName)
        private val itemTempTextView: TextView = itemView.findViewById(R.id.txtCityTemp)
        private val itemWeatherTextView: TextView = itemView.findViewById(R.id.txtCityWeather)
        private val imgWeather: ImageView = itemView.findViewById(R.id.imgWeather)


        private var currentItem: Place? = null

        fun bind(item: Place) {
            currentItem = item
            itemNameTextView.text = item.name
            itemTempTextView.text = item.main.temp
            itemWeatherTextView.text = item.weather.last().main
            val icon = item.weather.last().icon
            val url = "http://openweathermap.org/img/wn/$icon@4x.png"
            Glide.with(itemView).load(url).into(imgWeather)

        }

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id.equals(newItem.id)
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.equals(newItem)
    }
}