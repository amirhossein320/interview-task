package ir.sanags.interviewTask.presenter.addresses

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.sanags.interviewTask.R
import ir.sanags.interviewTask.data.api.address.AddressResponse
import ir.sanags.interviewTask.databinding.FragmentAddressesBinding
import ir.sanags.interviewTask.presenter.base.BaseFragment
import ir.sanags.interviewTask.presenter.base.UiState
import ir.sanags.interviewTask.util.gone
import ir.sanags.interviewTask.util.visible

class AddressesFragment :
    BaseFragment<FragmentAddressesBinding>(FragmentAddressesBinding::inflate) {

    private lateinit var viewModel: AddressesViewModel
    private lateinit var addressesAdapter: AddressesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            viewModelFactory {
                initializer {
                    AddressesViewModel(getInjection().addressRepository())
                }
            }
        )[AddressesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecycler()
        onAddAddressClick()
        manageState()
        with(viewModel) {
            if (isFirstTime || isGoneToAddFragment) {
                isFirstTime = false
                getAddresses()
            } else addressesAdapter.submitList(addresses)
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            toolbarTitle.text = getString(R.string.addresses)
            toolbarIcon.gone()
        }
    }

    private fun onAddAddressClick() {
    }

    private fun manageState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->

                when (uiState) {
                    is UiState.Loading -> {
                        manageUi(isLoading = true)
                    }
                    is UiState.Data -> {
                        manageUi(data = uiState.data as List<AddressResponse>)
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

    private fun manageUi(
        data: List<AddressResponse>? = null,
        error: String? = null,
        isLoading: Boolean = false
    ) {
        with(binding) {
            if (isLoading) {
                uiState.progressBar.visible()
                uiState.txtMessage.gone()
                recycler.gone()
            } else {
                data?.let {
                    uiState.progressBar.gone()
                    uiState.txtMessage.gone()
                    recycler.visible()
                    addressesAdapter.submitList(it)//set data
                } ?: run {
                    error?.let {//show error
                        recycler.gone()
                        uiState.progressBar.gone()
                        uiState.txtMessage.apply {
                            visible()
                            text = it
                        }
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        addressesAdapter = AddressesAdapter()
        binding.recycler.apply {
            layoutManager =
                if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(
                    context
                )
                else GridLayoutManager(context, 2)

            adapter = addressesAdapter
        }
    }
}