package kr.co.main.calendar.mapper

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.FarmWorkEntity
import kr.co.main.calendar.model.FarmWorkModel

internal object FarmWorkModelMapper
    : Mapper<FarmWorkEntity, FarmWorkModel> {
    override fun convert(param: FarmWorkEntity): FarmWorkModel =
        with(param) {
            FarmWorkModel(
                id = id,
                crop = CropModelMapper.toRight(crop),
                startMonth = startMonth,
                startEra = startEra,
                endMonth = endMonth,
                endEra = endEra,
                category = category,
                farmWork = farmWork,
                videoUrl = videoUrl
            )
        }
}