package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AlarmStatusData
import kr.co.remote.model.response.fcm.GetAlarmStatusResponse

internal object AlarmStatusRemoteMapper :
    Mapper<GetAlarmStatusResponse, AlarmStatusData> {
    override fun convert(param: GetAlarmStatusResponse): AlarmStatusData {
        return AlarmStatusData(
            commentAlarm = param.commentAlarm,
            scheduleAlarm = param.scheduleAlarm
        )
    }
}