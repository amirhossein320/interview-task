package ir.sanags.interviewTask.data.api.address

import ir.sanags.interviewTask.data.api.BaseRepository
import retrofit2.Response

class AddressRepository(
    private val addressService: AddressService
) : BaseRepository() {

    suspend fun all(
        onSuccess: suspend (List<AddressResponse>) -> Unit,
        onFailure: suspend (message: String) -> Unit
    ) = fetch({ addressService.all() }, onSuccess, onFailure)

    suspend fun add(
        address: AddressDto,
        onSuccess: suspend (AddressResponse) -> Unit,
        onFailure: suspend (message: String) -> Unit
    ) = fetch({ addressService.add(address) }, onSuccess, onFailure)

}