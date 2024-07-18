package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.ScheduleData
import kr.co.domain.entity.ScheduleEntity
import kr.co.domain.entity.type.ScheduleType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal object ScheduleMapper
    : Mapper<ScheduleData, ScheduleEntity> {
    override fun convert(param: ScheduleData): ScheduleEntity =
        with(param) {
            ScheduleEntity(
                id = id,
                type = ScheduleType.ofValue(category),
                title = title,
                startDate = LocalDate.parse(startDate),
                endDate = LocalDate.parse(endDate),
                memo = memo,
                isAlarmOn = alarmOn,
                alarmDateTime =
                if (alarmDateTime.isBlank()) null
                else LocalDateTime.parse(alarmDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            )
        }
}
