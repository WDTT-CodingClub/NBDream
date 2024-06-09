package kr.co.main.calendar.model

import kr.co.domain.entity.FarmWorkEntity

data class FarmWorkModel(
    val id:String,
    val crop: CropModel,
    val startEra: FarmWorkEntity.Era,
    val endEra: FarmWorkEntity.Era,
    val category: FarmWorkEntity.Category, //농작업 타입 (ex. 생육과정(주요농작업))
    val farmWork:String, //농작업 (ex. 객토, 퇴비 주기)
    val videoUrl: String? = null
)

internal fun FarmWorkEntity.convert() = FarmWorkModel(
    id = id,
    crop = crop.convert(),
    startEra = startEra,
    endEra = endEra,
    category = category,
    farmWork = farmWork,
    videoUrl = videoUrl
)
internal fun FarmWorkModel.convert() = FarmWorkEntity(
    id = id,
    crop = crop.convert(),
    startEra = startEra,
    endEra = endEra,
    category = category,
    farmWork = farmWork,
    videoUrl = videoUrl
)