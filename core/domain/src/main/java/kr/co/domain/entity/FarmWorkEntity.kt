package kr.co.domain.entity

import kr.co.domain.entity.DreamCrop

data class FarmWorkEntity(
    val dreamCrop: DreamCrop,
    val startMonth: Int,
    val startEra: CropScheduleEra,
    val endMonth:Int,
    val endEra: CropScheduleEra,
    val farmWorkTypeCode: String, //농작업 타입 코드
    val farmWorkTypeName: String, //농작업 타입 (ex. 생육과정(주요농작업))
    val farmWork:String, //농작업 (ex. 객토, 퇴비 주기)
    val videoUrl: String? = null
) {

    enum class CropScheduleEra {
        EARLY,
        MID,
        LATE;

        companion object {
            fun getEraByString(era: String): CropScheduleEra = when (era) {
                "상" -> EARLY
                "중" -> MID
                "하" -> LATE
                else -> throw IllegalArgumentException("Unknown era")
            }
        }
    }
}