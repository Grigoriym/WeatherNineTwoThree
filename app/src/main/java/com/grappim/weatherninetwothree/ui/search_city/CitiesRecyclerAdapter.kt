package com.grappim.weatherninetwothree.ui.search_city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grappim.weatherninetwothree.databinding.LayoutSearchCityBinding
import com.grappim.weatherninetwothree.domain.FoundLocation
import kotlin.properties.Delegates

class CitiesRecyclerAdapter(
    private val onItemClicked: (FoundLocation) -> Unit
) : ListAdapter<FoundLocation, CitiesRecyclerAdapter.FoundLocationViewHolder>(CitiesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundLocationViewHolder {
        val binding = LayoutSearchCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoundLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoundLocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoundLocationViewHolder(
        private val binding: LayoutSearchCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: FoundLocation

        init {
            binding.searchCityContainer.setOnClickListener {
                onItemClicked(item)
            }
        }

        fun bind(foundLocation: FoundLocation) {
            this.item = foundLocation
            with(binding) {
                tvTitle.text = foundLocation.cityName
            }
        }
    }
}