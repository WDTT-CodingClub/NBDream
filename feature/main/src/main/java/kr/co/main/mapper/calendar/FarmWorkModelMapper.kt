package kr.co.main.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.FarmWorkEntity
import kr.co.main.model.calendar.FarmWorkModel

internal object FarmWorkModelMapper
    : Mapper<FarmWorkEntity, FarmWorkModel> {
    override fun convert(param: FarmWorkEntity): FarmWorkModel =
        with(param) {
            FarmWorkModel(
                id = id,
                startMonth = startMonth,
                startEra = startEra,
                endMonth = endMonth,
                endEra = endEra,
                type = type,
                farmWork = farmWork,
                videoUrl = videoUrl
            )
        }
}