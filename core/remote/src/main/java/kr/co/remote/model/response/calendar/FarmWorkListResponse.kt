package kr.co.remote.model.response.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class FarmWorkListResponse(
    @SerialName("farmWorkResDtoList")
    val farmWorkResponse: List<FarmWorkResponse>
){
    @Serializable
    data class FarmWorkResponse(
        val id: Int,
        val startMonth: Int,
        val startEra: String,
        val endMonth: Int,
        val endEra: String,
        val farmWorkCategory:String,
        val farmWork: String,
        val videoUrl:String
    )
}


