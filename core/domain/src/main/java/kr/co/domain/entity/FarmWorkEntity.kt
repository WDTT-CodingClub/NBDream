package kr.co.domain.entity

import kr.co.domain.entity.plzLookThisPakage.DreamCrop

data class FarmWorkEntity(
    val id:String,
    val dreamCrop: DreamCrop,
    val startEra: FarmWorkEra,
    val endEra: FarmWorkEra,
    val category: FarmWorkCategory, //농작업 타입 (ex. 생육과정(주요농작업))
    val farmWork:String, //농작업 (ex. 객토, 퇴비 주기)
    val videoUrl: String? = null
)

enum class FarmWorkEra(val value:Int){
    EARLY(1),
    MID(2),
    LATE(3)
}

enum class FarmWorkCategory{
    GROWTH,
    PEST,
    CLIMATE
}
