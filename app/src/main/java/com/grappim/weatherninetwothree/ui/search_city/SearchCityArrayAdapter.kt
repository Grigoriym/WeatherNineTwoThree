package com.grappim.weatherninetwothree.ui.search_city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.domain.FoundLocation

class SearchCityArrayAdapter(
    context: Context,
    countryList: List<FoundLocation> = mutableListOf()
) : ArrayAdapter<FoundLocation>(context, 0, countryList) {

    private val locationsList: ArrayList<FoundLocation> = ArrayList(countryList)

    override fun getFilter(): Filter = locationFilter

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val foundLocation: FoundLocation? = getItem(position)
        var newConvertView = convertView

        if (newConvertView == null) {
            newConvertView = LayoutInflater.from(context).inflate(
                R.layout.layout_search_city,
                parent,
                false
            )
        }
        requireNotNull(newConvertView)

        val textViewName = newConvertView.findViewById<TextView>(R.id.tvTitle)
        foundLocation?.let {
            textViewName.text = it.cityName
        }
        return newConvertView
    }

    fun getItemNotNull(position: Int): FoundLocation =
        getItem(position) ?: error("Cannot find item on position: $position")

    fun submitData(list: List<FoundLocation>) {
        this.clear()
        this.addAll(list)
    }

    private val locationFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = locationsList
            results.count = locationsList.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            clear()
            addAll(results?.values as MutableList<FoundLocation>)
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as FoundLocation).cityName
        }
    }
}