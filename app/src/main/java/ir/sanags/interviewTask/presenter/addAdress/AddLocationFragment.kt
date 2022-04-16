package ir.sanags.interviewTask.presenter.addAdress

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ir.sanags.interviewTask.R
import ir.sanags.interviewTask.data.api.address.AddressResponse
import ir.sanags.interviewTask.databinding.FragmentAddLocationBinding
import ir.sanags.interviewTask.presenter.base.BaseFragment
import ir.sanags.interviewTask.presenter.base.UiState
import ir.sanags.interviewTask.util.gone
import ir.sanags.interviewTask.util.visible

class AddLocationFragment :
    BaseFragment<FragmentAddLocationBinding>(FragmentAddLocationBinding::inflate) {

    private lateinit var viewModel: AddAddressViewModel
    private var map: GoogleMap? = null
    private var selectedLocation: LatLng? = null
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val tehran = LatLng(35.6891975, 51.3889736)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tehran, 14f))

        googleMap.setOnMapClickListener { latLng ->
            selectedLocation = latLng
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireParentFragment(),
            viewModelFactory {
                initializer {
                    AddAddressViewModel(getInjection().addressRepository())
                }
            }
        )[AddAddressViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupMap()
        onSaveLocationClick()
        manageState()
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            toolbarTitle.text = getString(R.string.map)
            toolbarIcon.setOnClickListener {
                popBack()
            }
        }
    }

    private fun manageState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        manageUi(isLoading = true)
                    }
                    is UiState.Data -> {
                        manageUi(data = uiState.data as AddressResponse)
                    }
                    is UiState.Error -> {
                        manageUi(error = uiState.message)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun manageUi(
        data: AddressResponse? = null,
        error: String? = null,
        isLoading: Boolean = false
    ) {
        with(binding) {
            if (isLoading) {
                progressBar.visible()
                btnSave.isEnabled = false
            } else {
                data?.let {
                    showToastMessage(getString(R.string.saved))
                   popBack()
                } ?: run {
                    error?.let {//show error
                        btnSave.isEnabled = true
                        progressBar.gone()
                        showSnackMessage(error)
                    }
                }
            }
        }
    }

    private fun onSaveLocationClick() {
        binding.btnSave.setOnClickListener {
            selectedLocation?.let {
                viewModel.addAddress()
            } ?: showSnackMessage(getString(R.string.errSelectLocation))
        }
    }
}