package kr.co.data.model.data.user

data class UserData(
    val name: String,
    val profileImage: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val crops: List<String>
)