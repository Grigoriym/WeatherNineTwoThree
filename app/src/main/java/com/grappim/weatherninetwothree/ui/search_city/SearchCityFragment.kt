package com.grappim.weatherninetwothree.ui.search_city

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.core.location.LocationResult
import com.grappim.weatherninetwothree.core.location.NineTwoThreeLocationManager
import com.grappim.weatherninetwothree.core.location.NineTwoThreeLocationManagerImpl
import com.grappim.weatherninetwothree.databinding.FragmentSearchCityBinding
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.ui.weather_details.WeatherDetailsFragment
import com.grappim.weatherninetwothree.utils.extensions.hideSoftKeyboard
import com.grappim.weatherninetwothree.utils.extensions.isPermissionGranted
import com.grappim.weatherninetwothree.utils.extensions.launchAndRepeatWithViewLifecycle
import com.grappim.weatherninetwothree.utils.extensions.showSnackbar
import com.grappim.weatherninetwothree.utils.locationPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentSearchCityBinding>(
    FragmentSearchCityBinding::inflate
) {

    private val viewModel by viewModels<SearchCityViewModel>()

    private val arrayAdapter by lazy {
        SearchCityArrayAdapter(requireContext())
    }

    private val locationManager: NineTwoThreeLocationManager by lazy {
        NineTwoThreeLocationManagerImpl()
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                initLocationUpdates()
            }
            else -> {
                binding.root.showSnackbar(
                    R.string.location_permission_denied,
                    Snackbar.LENGTH_SHORT,
                    R.string.ok
                ) {
                    goToAppPermissions()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreState(savedInstanceState)
        initViews()
    }

    override fun onStop() {
        super.onStop()
        locationManager.stopLocationManager()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ARG_KEY_BTN_OK_VISIBILITY, viewModel.isButtonOkVisible)
        viewModel.latitude?.let { outState.putDouble(ARG_KEY_LATITUDE, it) }
        viewModel.longitude?.let { outState.putDouble(ARG_KEY_LONGITUDE, it) }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            binding.btnOk.isVisible = savedInstanceState.getBoolean(ARG_KEY_BTN_OK_VISIBILITY)
            viewModel.isButtonOkVisible = binding.btnOk.isVisible
        } else {
            binding.btnOk.isVisible = viewModel.isButtonOkVisible
        }
    }

    private fun initViews() {
        with(binding) {
            etCity.textChanges()
                .filter {
                    it.toString().isNotEmpty()
                }
                .drop(1)
                .onEach { event ->
                    if (!viewModel.isGettingCurrentPosition &&
                        !etCity.isPerformingCompletion
                    ) {
                        btnOk.isVisible = false
                        viewModel.isButtonOkVisible = false
                    }
                    if (!etCity.isPerformingCompletion) {
                        viewModel.searchLocation(event.toString())
                    }
                    if (viewModel.isGettingCurrentPosition) {
                        viewModel.isGettingCurrentPosition = false
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
            etCity.setOnItemClickListener { _, _, position, _ ->
                btnOk.isVisible = true
                viewModel.isButtonOkVisible = true
                viewModel.longitude = arrayAdapter.getItemNotNull(position).longitude
                viewModel.latitude = arrayAdapter.getItemNotNull(position).latitude
            }
            etCity.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        goToWeatherDetails()
                        true
                    }
                    else -> false
                }
            }
            etCity.setAdapter(arrayAdapter)

            btnOk.setOnClickListener {
                goToWeatherDetails()
            }
            btnSearch.setOnClickListener {
                launchLocationManager()
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.foundLocation.collect {
                binding.etCity.setText(it)
                binding.btnOk.isVisible = true
                viewModel.isButtonOkVisible = true
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.searchInput.collect {
                when (it) {
                    is Try.Success -> {
                        arrayAdapter.submitData(it.data)
                    }
                }
            }
        }
    }

    private fun goToWeatherDetails() {
        hideSoftKeyboard()
        findNavController().navigate(
            R.id.action_fragmentSearchCity_to_fragmentWeatherDetails,
            WeatherDetailsFragment.getBundleForDetails(
                name = binding.etCity.text.toString(),
                longitude = requireNotNull(viewModel.longitude),
                latitude = requireNotNull(viewModel.latitude)
            )
        )
    }

    private fun launchLocationManager() {
        if (isLocationPermissionGranted()) {
            initLocationUpdates()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            binding.root.showSnackbar(
                R.string.location_permission_needed,
                Snackbar.LENGTH_INDEFINITE,
                R.string.ok
            ) {
                launchLocationPermissionRequest()
            }
        } else {
            binding.root.showSnackbar(
                R.string.location_permission_not_available,
                Snackbar.LENGTH_LONG,
                R.string.ok
            ) {
                launchLocationPermissionRequest()
            }
        }
    }

    private fun launchLocationPermissionRequest() {
        locationPermissionRequest.launch(locationPermissions)
    }

    private fun goToAppPermissions() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun isLocationPermissionGranted(): Boolean =
        (requireContext().isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                requireContext().isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))

    private fun initLocationUpdates() {
        locationManager.getCurrentLocation(requireContext()) { locationResult ->
            when (locationResult) {
                is LocationResult.Success -> {
                    viewModel.latitude = locationResult.latitude
                    viewModel.longitude = locationResult.longitude
                    viewModel.getCurrentPosition()
                }
                is LocationResult.Error -> {

                }
            }
        }
    }

    companion object {
        const val ARG_KEY_BTN_OK_VISIBILITY = "arg.key.btn.ok.visibility"
        const val ARG_KEY_LONGITUDE = "arg.key.longitude"
        const val ARG_KEY_LATITUDE = "arg.key.latitude"
    }

}