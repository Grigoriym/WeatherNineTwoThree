package com.grappim.weatherninetwothree.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.grappim.weatherninetwothree.core.navigation.NavigationManager
import javax.inject.Inject

/**
 * Base class for fragment which is used for:
 * 1) initializing and clearing viewBinding
 */
open class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var _binding: VB? = null
    val binding: VB get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}