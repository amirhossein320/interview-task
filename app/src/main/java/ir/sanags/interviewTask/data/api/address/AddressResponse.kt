package ir.sanags.interviewTask.data.api.address

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    val id: Int,
    val address: String,
    @SerializedName("coordinate_mobile") val coordinateMobile: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
)


data class AddressDto(
    val region: Int = 1,
    var address: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("coordinate_mobile") var coordinateMobile: String,
    @SerializedName("coordinate_phone_number") var coordinatePhoneNumber: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    var gender: String
){

    companion object {
        fun init() = AddressDto(
            address = "", latitude = 0.0, longitude = 0.0,
            coordinateMobile = "", coordinatePhoneNumber = "",
            lastName = "", firstName = "", gender = Gender.MALE.gender
        )
    }

}


enum class Gender(val gender: String) { MALE("Male"), FEMALE("Female") }
