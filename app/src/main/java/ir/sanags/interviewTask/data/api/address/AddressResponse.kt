package ir.sanags.interviewTask.data.api.address

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    val id: Int,
    val address: String,
    @SerializedName("coordinate_mobile") val coordinateMobile: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
)


data class AddressDto(
    val region: Int = 1,
    val address: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("coordinate_mobile") val coordinateMobile: Long,
    @SerializedName("coordinate_phone_number") val coordinatePhoneNumber: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val gender: String
)


enum class Gender(gender: String) { MALE("Male"), FEMALE("Female") }
