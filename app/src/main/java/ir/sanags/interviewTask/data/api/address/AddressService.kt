package ir.sanags.interviewTask.data.api.address

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AddressService {

    @GET("address")
    suspend fun all(): Response<List<AddressResponse>>

    @POST("address")
    suspend fun add(@Body address: AddressDto): Response<AddressResponse>

}