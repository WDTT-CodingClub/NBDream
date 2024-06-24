package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.type.ScheduleType
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.type.ScheduleModelType

internal object ScheduleModelTypeMapper
    : BaseMapper<ScheduleType, ScheduleModelType>() {
    override fun toRight(left: ScheduleType): ScheduleModelType = when (left) {
        is ScheduleType.All -> ScheduleModelType.All
        is ScheduleType.Crop -> ScheduleModelType.Crop(
            CropModel.create(CropModelTypeMapper.toRight(left.cropType))
        )
    }

    override fun toLeft(right: ScheduleModelType): ScheduleType = when (right) {
        is ScheduleModelType.All -> ScheduleType.All
        is ScheduleModelType.Crop -> ScheduleType.Crop(
            CropModelTypeMapper.toLeft(right.cropModel.type)
        )
    }
}