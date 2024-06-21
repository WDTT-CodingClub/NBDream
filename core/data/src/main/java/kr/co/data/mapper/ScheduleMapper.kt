package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.ScheduleData
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.ScheduleEntity
import java.time.LocalDate
import java.time.LocalDateTime

internal object ScheduleMapper
    : Mapper<ScheduleData, ScheduleEntity> {
    override fun convert(param: ScheduleData): ScheduleEntity =
        with(param) {
            ScheduleEntity(
                id = id,
                category = category.toCategory(),
                title = title,
                startDate = LocalDate.parse(startDate),
                endDate = LocalDate.parse(startDate),
                memo = memo,
                isAlarmOn = isAlarmOn,
                alarmDateTime = LocalDateTime.parse(alarmDateTime)
            )
        }
}

private fun String.toCategory(): ScheduleEntity.Category =
    ScheduleEntity.Category.All.let {
        if (this == it.koreanName) it
        else ScheduleEntity.Category.Crop(CropEntity.getCropEntity(this))
    }
