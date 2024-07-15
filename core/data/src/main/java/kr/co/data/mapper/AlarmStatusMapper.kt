package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AlarmStatusData
import kr.co.domain.entity.AlarmStatusEntity

internal object AlarmStatusMapper : Mapper<AlarmStatusData, AlarmStatusEntity> {
    override fun convert(param: AlarmStatusData): AlarmStatusEntity {
        return AlarmStatusEntity(
            commentAlarm = param.commentAlarm,
            scheduleAlarm = param.scheduleAlarm
        )
    }
}