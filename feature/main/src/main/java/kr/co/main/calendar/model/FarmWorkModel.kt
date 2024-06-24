package kr.co.main.calendar.model

import kr.co.domain.entity.type.FarmWorkType
import kr.co.domain.entity.type.FarmWorkEraType

internal data class FarmWorkModel(
    val id: Int,
    val startMonth: Int,
    val startEra: FarmWorkEraType,
    val endMonth: Int,
    val endEra: FarmWorkEraType,
    val type: FarmWorkType,
    val farmWork: String,
    val videoUrl: String? = null
)

