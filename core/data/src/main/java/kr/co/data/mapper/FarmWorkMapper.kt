package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.FarmWorkData
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.entity.type.FarmWorkType
import kr.co.domain.entity.type.FarmWorkEraType

internal object FarmWorkMapper
    : Mapper<FarmWorkData, FarmWorkEntity> {
    override fun convert(param: FarmWorkData): FarmWorkEntity =
        with(param) {
            FarmWorkEntity(
                id = id,
                startMonth = startMonth,
                startEra = FarmWorkEraType.ofValue(startEra),
                endMonth = endMonth,
                endEra = FarmWorkEraType.ofValue(endEra),
                type = FarmWorkType.ofValue(farmWorkCategory),
                farmWork = farmWork,
                videoUrl = videoUrl
            )
        }
}