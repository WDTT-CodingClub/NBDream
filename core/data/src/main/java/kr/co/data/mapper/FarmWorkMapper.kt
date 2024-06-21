package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.FarmWorkData
import kr.co.domain.entity.FarmWorkEntity

internal object FarmWorkMapper
    : Mapper<FarmWorkData, FarmWorkEntity> {
    override fun convert(param: FarmWorkData): FarmWorkEntity =
        with(param) {
            FarmWorkEntity(
                id = id,
                startMonth = startMonth,
                startEra = FarmWorkEntity.Era.getEra(startEra),
                endMonth = endMonth,
                endEra = FarmWorkEntity.Era.valueOf(endEra),
                category = FarmWorkEntity.Category.getCategory(farmWorkCategory),
                farmWork = farmWork
            )
        }
}