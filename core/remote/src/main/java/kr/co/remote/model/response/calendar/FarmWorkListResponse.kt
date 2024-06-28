package kr.co.remote.model.response.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class FarmWorkListResponse(
    @SerialName("farmWorkResDtoList")
    val farmWorkList: List<FarmWorkResponse>
){
    @Serializable
    data class FarmWorkResponse(
        val id: Long,
        val startEra: String,
        val startMonth: Int,
        val endMonth: Int,
        val endEra: String,
        val farmWorkCategory:String,
        val farmWork: String,
        val videoUrl:String?
    )
}


