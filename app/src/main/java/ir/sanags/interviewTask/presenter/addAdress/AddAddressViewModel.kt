package ir.sanags.interviewTask.presenter.addAdress

import androidx.lifecycle.viewModelScope
import ir.sanags.interviewTask.data.api.address.AddressDto
import ir.sanags.interviewTask.data.api.address.AddressRepository
import ir.sanags.interviewTask.presenter.base.BaseViewModel
import ir.sanags.interviewTask.presenter.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAddressViewModel(private val addressRepository: AddressRepository) : BaseViewModel() {

    var address: AddressDto = AddressDto.init()

    fun addAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState.Loading)
            addressRepository.add(address,
                onSuccess = { data ->
                    _uiState.emit(UiState.Data(data))
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


