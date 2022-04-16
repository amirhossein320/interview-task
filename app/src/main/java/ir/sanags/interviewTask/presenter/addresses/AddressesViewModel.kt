package ir.sanags.interviewTask.presenter.addresses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.sanags.interviewTask.data.api.address.AddressRepository
import ir.sanags.interviewTask.data.api.address.AddressResponse
import ir.sanags.interviewTask.presenter.base.BaseViewModel
import ir.sanags.interviewTask.presenter.base.UiState
import ir.sanags.interviewTask.util.ERR_EMPTY_ADDRESSES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressesViewModel(private val addressRepository: AddressRepository) : BaseViewModel() {

    var isGoneToAddFragment = false
    var isFirstTime = true

    var addresses = listOf<AddressResponse>()
        private set

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState.Loading)
            addressRepository.all(
                onSuccess = {
                    it?.let { data ->
                        if (data.isEmpty()) _uiState.emit(UiState.Error(ERR_EMPTY_ADDRESSES))
                        else {
                            addresses = data
                            _uiState.emit(UiState.Data(addresses))
                        }
                    }
                },
                onFailure = {
                    it?.let { message ->
                        _uiState.emit(UiState.Error(message))
                    }
                }
            )
        }
    }
}


