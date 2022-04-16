package ir.sanags.interviewTask.presenter.addAdress

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ir.sanags.interviewTask.R
import ir.sanags.interviewTask.data.api.address.AddressDto
import ir.sanags.interviewTask.data.api.address.AddressResponse
import ir.sanags.interviewTask.data.api.address.Gender
import ir.sanags.interviewTask.databinding.FragmentAddAddressBinding
import ir.sanags.interviewTask.presenter.base.BaseFragment
import ir.sanags.interviewTask.presenter.base.UiState
import ir.sanags.interviewTask.presenter.main.MainViewModel
import ir.sanags.interviewTask.util.clear
import ir.sanags.interviewTask.util.gone
import ir.sanags.interviewTask.util.isEmpty
import ir.sanags.interviewTask.util.visible

class AddAddressFragment :
    BaseFragment<FragmentAddAddressBinding>(FragmentAddAddressBinding::inflate) {

    private lateinit var viewModel: AddAddressViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            viewModelFactory {
                initializer { AddAddressViewModel(getInjection().addressRepository()) }
            })[AddAddressViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        onNextStepClick()
        manageState()
        with(binding) {
            handleEdtHasData(edtName)
            handleEdtHasData(edtLastName, 4)
            handleEdtHasData(edtMobile, 12)
            handleEdtHasData(edtPhoneNumber, 12)
            handleEdtHasData(edtAddress, 15)
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            toolbarTitle.text = getString(R.string.add_address)
            toolbarIcon.setOnClickListener {
                popBack()
            }
        }
    }

    private fun manageState() {
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Data -> {
                        with(binding) {
                            edtName.clear()
                            edtLastName.clear()
                            edtMobile.clear()
                            edtPhoneNumber.clear()
                            edtAddress.clear()
                        }
                        viewModel.address = AddressDto.init()
                        mainViewModel.hasNewAddress = true
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun onNextStepClick() {
        with(binding) {
            btnNext.setOnClickListener {
                when {
                    edtName.isEmpty() or (edtName.text.length < 3)
                    -> showSnackMessage(getString(R.string.errNameEmpty))
                    edtLastName.isEmpty() or (edtLastName.text.length < 4)
                    -> showSnackMessage(getString(R.string.errLastNameEmpty))
                    edtPhoneNumber.isEmpty() or (edtPhoneNumber.text.length < 12)
                    -> showSnackMessage(getString(R.string.errPhoneEmpty))
                    edtMobile.isEmpty() or (edtMobile.text.length < 12)
                    -> showSnackMessage(getString(R.string.errMobileEmpty))
                    edtAddress.isEmpty() or (edtAddress.text.length < 15)
                    -> showSnackMessage(getString(R.string.errAddressEmpty))
                    else -> {
                        viewModel.address.apply {
                            firstName = edtName.text.toString()
                            lastName = edtLastName.text.toString()
                            coordinatePhoneNumber = edtPhoneNumber.text.toString()
                            coordinateMobile = edtMobile.text.toString()
                            address = edtAddress.text.toString()
                            gender =
                                if (swGender.isChecked) Gender.FEMALE.gender else Gender.MALE.gender
                        }
                        navigateToChild(AddLocationFragment(), R.id.childContainer)
                    }
                }
            }
        }
    }

    private fun handleEdtHasData(edt: EditText, length: Int = 3) {
        edt.apply {
            addTextChangedListener {
                it?.let { editable ->
                    if (editable.length >= length) {
                        edt.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_green_check_circle_24
                            ), null, null, null
                        )
                    } else {
                        edt.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_grey_check_circle_24
                            ), null, null, null
                        )
                    }
                }
            }
        }
    }
}