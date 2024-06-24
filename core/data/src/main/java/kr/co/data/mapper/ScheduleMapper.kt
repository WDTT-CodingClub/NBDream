package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.ScheduleData
import kr.co.domain.entity.ScheduleEntity
import kr.co.domain.entity.type.ScheduleType
import java.time.LocalDate
import java.time.LocalDateTime

internal object ScheduleMapper
    : Mapper<ScheduleData, ScheduleEntity> {
    override fun convert(param: ScheduleData): ScheduleEntity =
        with(param) {
            ScheduleEntity(
                id = id,
                type = ScheduleType.ofValue(category),
                title = title,
                startDate = LocalDate.parse(startDate),
                endDate = LocalDate.parse(startDate),
                memo = memo,
                isAlarmOn = isAlarmOn,
                alarmDateTime = LocalDateTime.parse(alarmDateTime)
            )
        }
}
