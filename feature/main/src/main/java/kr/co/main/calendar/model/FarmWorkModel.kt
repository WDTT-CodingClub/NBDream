package kr.co.main.calendar.model

import kr.co.domain.entity.FarmWorkEntity

data class FarmWorkModel(
    val id: Int,
    val startMonth: Int,
    val startEra: FarmWorkEntity.Era,
    val endMonth: Int,
    val endEra: FarmWorkEntity.Era,
    val category: FarmWorkEntity.Category,
    val farmWork: String,
    val videoUrl: String? = null
)

