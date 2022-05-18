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
import com.google.android.material.snackbar.Snackbar
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.core.base.BaseFragment
import com.grappim.weatherninetwothree.core.location.LocationResult
import com.grappim.weatherninetwothree.core.location.NineTwoThreeLocationManager
import com.grappim.weatherninetwothree.core.location.NineTwoThreeLocationManagerImpl
import com.grappim.weatherninetwothree.databinding.FragmentSearchCityBinding
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.model.location.FoundLocation
import com.grappim.weatherninetwothree.ui.weather_details.WeatherDetailsFragment
import com.grappim.weatherninetwothree.utils.extensions.*
import com.grappim.weatherninetwothree.utils.permission.locationPermissions
import com.grappim.weatherninetwothree.utils.views.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.afterTextChanges

@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentSearchCityBinding>(
    FragmentSearchCityBinding::inflate
) {

    private val viewModel by viewModels<SearchCityViewModel>()

    private val locationManager: NineTwoThreeLocationManager by lazy {
        NineTwoThreeLocationManagerImpl()
    }

    private val adapter by lazy {
        CitiesRecyclerAdapter(::onFoundLocationClicked)
    }

    private var isFragmentRecreated: Boolean = false

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
        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ARG_KEY_BTN_OK_VISIBILITY, viewModel.isButtonOkVisible)
        viewModel.latitude?.let { outState.putDouble(ARG_KEY_LATITUDE, it) }
        viewModel.longitude?.let { outState.putDouble(ARG_KEY_LONGITUDE, it) }

        val foundLocationList = (adapter.currentList as? List<FoundLocation>)?.toTypedArray()
        outState.putSerializable(ARG_KEY_FOUND_CITIES, foundLocationList)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        isFragmentRecreated = savedInstanceState != null
        if (savedInstanceState != null) {
            setOkButtonVisibility(savedInstanceState.getBoolean(ARG_KEY_BTN_OK_VISIBILITY))
            val foundLocationList =
                (savedInstanceState.getSerializable(ARG_KEY_FOUND_CITIES) as? Array<FoundLocation>)
                    ?.toList() ?: emptyList()

            submitListToAdapter(foundLocationList)
        } else {
            binding.btnOk.isVisible = viewModel.isButtonOkVisible
        }
    }

    private fun observeViewModel() {
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.foundLocation.collect {
                    setEditCityText(it)
                }
            }
            launch {
                viewModel.searchInput.collect {
                    when (it) {
                        is Try.Success -> {
                            submitListToAdapter(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun submitListToAdapter(list: List<FoundLocation>) {
        binding.cardPlaces.fadeVisibility(list.isNotEmpty())
        adapter.submitList(list)
    }

    /**
     * Tag and focus are needed to fix excessive requests in some situations, so that
     * we only do searchLocation only when we type something in editText and
     * do nothing if we paste location from recyclerView or from getCurrentPlace
     */
    private fun setEditCityText(text: String) {
        with(binding) {
            val focussed = etCity.hasFocus()
            if (focussed) {
                etCity.clearFocus()
            }
            etCity.tag = text
            etCity.setText(text)
            etCity.setSelection(text.length)
            etCity.tag = null
        }
    }

    private fun initViews() {
        with(binding) {
            etCity.afterTextChanges()
                .skipInitialValue()
                .filter {
                    it.editable?.toString()?.isNotEmpty() == true
                }
                .onEach { text ->
                    val etTag = binding.etCity.tag
                    val etHasFocus = binding.etCity.hasFocus()
                    if (etTag == null && etHasFocus) {
                        viewModel.searchLocation(text.editable?.toString()!!)
                        setOkButtonVisibility(false)
                    } else {
                        if (isFragmentRecreated) {
                            isFragmentRecreated = false
                            setOkButtonVisibility(viewModel.isButtonOkVisible)
                        }
                        if (viewModel.isGetLocationClicked) {
                            viewModel.isGetLocationClicked = false
                            setOkButtonVisibility(true)
                        }
                        if (viewModel.isLocationSelectedFromList) {
                            viewModel.isLocationSelectedFromList = false
                            setOkButtonVisibility(true)
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            etCity.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        goToWeatherDetails()
                        true
                    }
                    else -> false
                }
            }

            btnOk.setOnClickListener {
                goToWeatherDetails()
            }
            btnSearch.setOnClickListener {
                launchLocationManager()
            }

            rvPlaces.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
            rvPlaces.adapter = adapter
        }
    }

    private fun onFoundLocationClicked(foundLocation: FoundLocation) {
        viewModel.isLocationSelectedFromList = true
        viewModel.longitude = foundLocation.longitude
        viewModel.latitude = foundLocation.latitude
        setEditCityText(foundLocation.cityName)
        clearAdapter()
    }

    private fun clearAdapter() {
        adapter.submitList(emptyList())
        binding.cardPlaces.fadeVisibility(false)
    }

    private fun setOkButtonVisibility(isVisible: Boolean) {
        viewModel.isButtonOkVisible = isVisible
        binding.btnOk.isVisible = isVisible
    }

    private fun goToWeatherDetails() {
        hideSoftKeyboard()
        navigationManager.goToDetails(
            WeatherDetailsFragment.getBundleForDetails(
                name = binding.etCity.text.toString(),
                longitude = requireNotNull(viewModel.longitude),
                latitude = requireNotNull(viewModel.latitude)
            )
        )
    }

    private fun launchLocationManager() {
        viewModel.isGetLocationClicked = true
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
        locationManager.getCurrentLocation(requireContext(), ::handleLocationUpdates)
    }

    private fun handleLocationUpdates(locationResult: LocationResult) {
        when (locationResult) {
            is LocationResult.Success -> {
                clearAdapter()
                viewModel.latitude = locationResult.latitude
                viewModel.longitude = locationResult.longitude
                viewModel.getCurrentPlace()
            }
            is LocationResult.Error -> {
                binding.root.showSnackbar(
                    msg = locationResult.errorMsg
                )
            }
        }
    }

    companion object {
        const val ARG_KEY_BTN_OK_VISIBILITY = "arg.key.btn.ok.visibility"
        const val ARG_KEY_LONGITUDE = "arg.key.longitude"
        const val ARG_KEY_LATITUDE = "arg.key.latitude"
        const val ARG_KEY_FOUND_CITIES = "arg.key.found.cities"
    }

}