package kr.co.main.calendar.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.ScheduleEntity
import kr.co.main.calendar.model.ScheduleModel

internal object ScheduleModelMapper
    : BaseMapper<ScheduleEntity, ScheduleModel>() {
    override fun toRight(left: ScheduleEntity): ScheduleModel =
        with(left) {
            ScheduleModel(
                id = id,
                category = when (category) {
                    is ScheduleEntity.Category.All -> ScheduleModel.Category.All
                    is ScheduleEntity.Category.Crop -> {
                        ScheduleModel.Category.Crop(
                            CropModelMapper.toRight(
                                (category as ScheduleEntity.Category.Crop).crop
                            )
                        )
                    }
                },
                title = title,
                startDate = startDate,
                endDate = endDate,
            )
        }

    override fun toLeft(right: ScheduleModel): ScheduleEntity =
        with(right) {
            ScheduleEntity(
                id = id,
                category = when (category) {
                    is ScheduleModel.Category.All -> ScheduleEntity.Category.All
                    is ScheduleModel.Category.Crop -> {
                        ScheduleEntity.Category.Crop(
                            CropModelMapper.toLeft(category.crop)
                        )
                    }
                },
                title = title,
                startDate = startDate,
                endDate = endDate,
            )
        }
}