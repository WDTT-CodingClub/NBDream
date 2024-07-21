package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AlarmStatusData
import kr.co.remote.model.response.alarm.GetAlarmStatusResponse

internal object AlarmStatusRemoteMapper :
    Mapper<GetAlarmStatusResponse, AlarmStatusData> {
    override fun convert(param: GetAlarmStatusResponse): AlarmStatusData =
        with(param.data) {
        AlarmStatusData(
            commentAlarm = commentAlarm,
            scheduleAlarm = scheduleAlarm
        )
    }
}