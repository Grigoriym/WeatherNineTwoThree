package com.grappim.weatherninetwothree.ui.search_city

import android.os.Bundle
import android.view.View
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.databinding.FragmentSearchCityBinding

class SearchCityFragment : BaseFragment<FragmentSearchCityBinding>(
    FragmentSearchCityBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {

        }
    }

}