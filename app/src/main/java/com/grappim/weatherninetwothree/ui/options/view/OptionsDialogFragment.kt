package com.grappim.weatherninetwothree.ui.options.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.navigation.NavigationManager
import com.grappim.weatherninetwothree.databinding.FragmentOptionsBinding
import com.grappim.weatherninetwothree.domain.model.base.Units
import com.grappim.weatherninetwothree.ui.options.viewModel.OptionsViewModel
import com.grappim.weatherninetwothree.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OptionsDialogFragment : DialogFragment(R.layout.fragment_options) {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewBinding by viewBinding(FragmentOptionsBinding::bind)

    private val viewModel by viewModels<OptionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun initViews() {
        with(viewBinding) {
            toggleGroup.selectButton(getIdToSelectButton())
            toggleGroup.setOnSelectListener { themedButton ->
                val selectedUnit = when (themedButton.id) {
                    btnC.id -> Units.METRIC
                    btnF.id -> Units.IMPERIAL
                    else -> Units.METRIC
                }
                viewModel.saveTemperatureUnit(selectedUnit)
            }
        }
    }

    @IdRes
    private fun getIdToSelectButton(): Int =
        when (viewModel.currentUnits) {
            Units.METRIC -> viewBinding.btnC.id
            Units.IMPERIAL -> viewBinding.btnF.id
        }
}