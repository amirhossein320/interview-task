package ir.sanags.interviewTask.data.api.address

import ir.sanags.interviewTask.data.api.BaseRepository

class AddressRepository(
    private val addressService: AddressService
) : BaseRepository() {

    suspend fun all(
        onSuccess: (List<AddressResponse>?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) = fetch(addressService.all(), onSuccess, onFailure)

    suspend fun add(
        address: AddressDto,
        onSuccess: (AddressResponse?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) = fetch(addressService.add(address), onSuccess, onFailure)
}