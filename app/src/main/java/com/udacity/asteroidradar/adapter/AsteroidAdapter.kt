package com.udacity.asteroidradar.adapter

import ClickHandler
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.model.Asteroid


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.databinding.AsteroidItemBinding


class AsteroidAdapter(private val clickHandler: ClickHandler) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsteroidAdapter.AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            clickHandler.onClick(asteroid)
        }
        holder.bind(asteroid)
    }


    inner class AsteroidViewHolder(private val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }
}

