package ir.sanags.interviewTask.di

import android.content.Context
import ir.sanags.interviewTask.data.api.RetrofitInitializer
import ir.sanags.interviewTask.data.api.address.AddressRepository
import ir.sanags.interviewTask.data.api.address.AddressService

class Container(context: Context) {

    private val retrofit by lazy { RetrofitInitializer() }
    val api by lazy { retrofit.retrofit(context) }

    fun addressService() = api.create(AddressService::class.java)

    fun addressRepository() = AddressRepository(addressService())
}