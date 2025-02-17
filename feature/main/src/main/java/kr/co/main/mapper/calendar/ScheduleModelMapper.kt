package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.ScheduleEntity
import kr.co.main.model.calendar.ScheduleModel

internal object ScheduleModelMapper
    : BaseMapper<ScheduleEntity, ScheduleModel>() {
    override fun toRight(left: ScheduleEntity): ScheduleModel =
        with(left) {
            ScheduleModel(
                id = id,
                type = ScheduleModelTypeMapper.toRight(type),
                title = title,
                startDate = startDate,
                endDate = endDate,
                memo = memo,
                isAlarmOn = isAlarmOn,
                alarmDateTime = alarmDateTime
            )
        }

    override fun toLeft(right: ScheduleModel): ScheduleEntity =
        with(right) {
            ScheduleEntity(
                id = id,
                type = ScheduleModelTypeMapper.toLeft(type),
                title = title,
                startDate = startDate,
                endDate = endDate,
                memo = memo,
                isAlarmOn = isAlarmOn,
                alarmDateTime = alarmDateTime
            )
        }
}